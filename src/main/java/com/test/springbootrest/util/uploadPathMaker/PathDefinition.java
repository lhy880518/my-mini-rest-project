package com.test.springbootrest.util.uploadPathMaker;


import com.test.springbootrest.util.uploadPathMaker.builder.Main;
import com.test.springbootrest.util.uploadPathMaker.builder.Resource;
import com.test.springbootrest.util.uploadPathMaker.builder.Root;
import com.test.springbootrest.util.uploadPathMaker.builder.Sub;

public enum PathDefinition {

    AWS_THUMBNAIL{
        public String getFullFilePath(){
            Resource resource = new Resource.Builder(Root.ISW_PRIVATE)
                    .with(Main.USER, null)
                    .with(Sub.THUMBNAIL, null)
                    .build();

            return resource.getFullPath();
        }
    };

    public abstract String getFullFilePath();
}
