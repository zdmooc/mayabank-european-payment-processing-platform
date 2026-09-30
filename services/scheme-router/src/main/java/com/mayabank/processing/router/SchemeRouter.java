package com.mayabank.processing.router;
import com.mayabank.processing.domain.*;
import java.util.EnumMap;
import java.util.Collection;

public final class SchemeRouter {
  private final EnumMap<Scheme,SchemeAuthorizationPort> routes=new EnumMap<>(Scheme.class);
  public SchemeRouter(Collection<SchemeAuthorizationPort> ports){ for(var p:ports) routes.put(p.scheme(),p); }
  public AuthorizationResult authorize(AuthorizationRequest request, SchemeScenario scenario){
    var port=routes.get(request.payment().scheme());
    if(port==null) throw new IllegalStateException("NO_ROUTE_FOR_SCHEME:"+request.payment().scheme());
    return port.authorize(request,scenario);
  }
}