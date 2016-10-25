/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.monitor.simple.pages;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.container.page.Page;
import com.alibaba.dubbo.container.page.PageHandler;
import com.alibaba.dubbo.monitor.simple.RegistryContainer;

import java.util.List;

/**
 * ShutdownPageHandler -- 停止某个host上所有的provider，用于停机升级。
 * 
 * @author Huanwen Qu
 */
public class ShutdownPageHandler implements PageHandler {

    public Page handle(URL url) {
        String host = url.getParameterAndDecoded("host");
        if (host == null || host.length() == 0) {
            throw new IllegalArgumentException("Please input host parameter.");
        }

        List<URL> providers = RegistryContainer.getInstance().getProvidersByHost(host);
        for(URL providerUrl : providers) {
            RegistryContainer.getInstance().getRegistry().unregister(providerUrl);
        }
        return new Page("<script type=\"text/javascript\">window.location.href=\"hosts.html\";</script>");
    }

}
