CREATE TABLE IF NOT EXISTS idempotency_claim (
  idempotency_key varchar(200) PRIMARY KEY,
  payment_id uuid NOT NULL,
  payload_hash varchar(128) NOT NULL,
  created_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS outbox_event (
  event_id uuid PRIMARY KEY,
  payment_id uuid NOT NULL,
  event_type varchar(100) NOT NULL,
  payload jsonb NOT NULL,
  status varchar(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING','PUBLISHED')),
  created_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS inbox_event (
  event_id uuid PRIMARY KEY,
  received_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS ledger_movement (
  movement_id uuid PRIMARY KEY,
  payment_id uuid NOT NULL,
  movement_type varchar(50) NOT NULL,
  business_reference varchar(200) NOT NULL UNIQUE,
  currency char(3) NOT NULL,
  created_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS ledger_line (
  line_id uuid PRIMARY KEY,
  movement_id uuid NOT NULL REFERENCES ledger_movement(movement_id),
  account varchar(100) NOT NULL,
  direction varchar(10) NOT NULL CHECK (direction IN ('DEBIT','CREDIT')),
  amount numeric(19,2) NOT NULL CHECK (amount > 0)
);

CREATE INDEX IF NOT EXISTS idx_outbox_pending ON outbox_event(status, created_at);
CREATE INDEX IF NOT EXISTS idx_ledger_payment ON ledger_movement(payment_id);
