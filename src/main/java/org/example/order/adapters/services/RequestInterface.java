package org.example.order.adapters.services;

import java.util.Map;

public interface RequestInterface {

    public void post(String path, Map<String, Object> jsonBody);

}
