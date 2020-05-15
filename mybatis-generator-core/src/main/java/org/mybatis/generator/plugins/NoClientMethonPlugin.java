/**
 * Copyright 2006-2015 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoClientMethonPlugin extends PluginAdapter {

    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ss HH:mm:ss");
        String formatDate = sdf.format(new Date());

        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * Dao层实现");
        interfaze.addJavaDocLine(" * 数据库表：" + introspectedTable.getFullyQualifiedTable());
        interfaze.addJavaDocLine(" * ");
        interfaze.addJavaDocLine(" * @author GengChao");
        interfaze.addJavaDocLine(" * @email chao_geng@sui.com");
        interfaze.addJavaDocLine(" * @date " + formatDate);
        interfaze.addJavaDocLine(" */");
        return true;
    }
}
