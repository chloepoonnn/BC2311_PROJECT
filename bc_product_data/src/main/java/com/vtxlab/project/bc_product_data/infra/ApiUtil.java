package com.vtxlab.project.bc_product_data.infra;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

public class ApiUtil {

  public static UriComponentsBuilder uriBuilder(UriScheme uriScheme,
      String domain, String path, String endpoint,
      MultiValueMap<String, String> queryParams, String... pathSegments) {
    return UriComponentsBuilder.newInstance()//
        .scheme(uriScheme.getProtocol())//
        .host(domain)//
        .pathSegment(pathSegments)//
        .path(path)//
        .pathSegment(endpoint)//
        .queryParams(queryParams);
  }

  public static UriComponentsBuilder uriBuilder(UriScheme uriScheme,
      String domain, String path, String endpoint) {
    return UriComponentsBuilder.newInstance()//
        .scheme(uriScheme.getProtocol())//
        .host(domain)//
        .pathSegment(path)//
        .pathSegment(endpoint.split("/"));
  }

  public static UriComponentsBuilder uriBuilder(UriScheme https, String domain,
      String valueOf, String path, String endpoint) {
    return UriComponentsBuilder.newInstance()//
        .scheme(https.getProtocol())//
        .host(domain)//
        .port(valueOf)//
        .pathSegment(path.split("/"))//
        .path(endpoint);
  }

  public static UriComponentsBuilder uriBuilder(UriScheme uriScheme,
      String domain) {
    return UriComponentsBuilder.newInstance()//
        .scheme(uriScheme.getProtocol())//
        .host(domain);
  }

  public static UriComponentsBuilder uriBuilder(UriScheme http, String domain,
      String endpoint) {
    return UriComponentsBuilder.newInstance()//
        .scheme(http.getProtocol())//
        .host(domain)//
        .pathSegment(endpoint.split("/"));
  }

  public static UriComponentsBuilder uriBuilder(UriScheme http, String domain,
      String[] endpoints) {
    return UriComponentsBuilder.newInstance()//
        .scheme(http.getProtocol())//
        .host(domain)//
        .pathSegment(endpoints);
  }
}
