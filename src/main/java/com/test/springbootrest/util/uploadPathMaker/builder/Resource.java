package com.test.springbootrest.util.uploadPathMaker.builder;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringJoiner;

public class Resource {

    private String fullPath;

    private Resource(Resource.Builder builder) {
        StringJoiner sj = new StringJoiner("/");

        Iterator<BaseEnumeration> iterator = builder.resources.keySet().iterator();
        while(iterator.hasNext()) {
            final BaseEnumeration resourceEnumeration = iterator.next();
            final Long resourceId = builder.resources.get(resourceEnumeration);

            sj.add(resourceEnumeration.getPath());
            if (resourceId != null) {
                sj.add(resourceId.toString());
            }
        }
        this.fullPath = sj.toString();
    }

    public static class Builder {
        private LinkedHashMap<BaseEnumeration, Long> resources;

        public Builder(BaseEnumeration root) {
            resources = new LinkedHashMap<BaseEnumeration, Long>();
            resources.put(root, null);
        }

        public Builder with(BaseEnumeration resource, Long resourceId) {
            this.resources.put(resource, resourceId);
            return this;
        }
        public Resource build() {
            return new Resource(this);
        }
    }

    public String getFullPath() {
        return fullPath;
    }
}