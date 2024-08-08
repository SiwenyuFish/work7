package com.spring.core.beans.factory.util;

import cn.hutool.core.lang.Assert;
import com.spring.core.beans.factory.HierarchicalBeanFactory;
import com.spring.core.beans.factory.ListableBeanFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanFactoryUtil {



    public static String[] beanNamesForTypeIncludingAncestors(ListableBeanFactory lbf, Class<?> type) {
        Assert.notNull(lbf, "ListableBeanFactory must not be null");
        String[] result = lbf.getBeanNamesForType(type);
        if (lbf instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory hbf = (HierarchicalBeanFactory)lbf;
            if (hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
                String[] parentResult = beanNamesForTypeIncludingAncestors((ListableBeanFactory)hbf.getParentBeanFactory(), type);
                result = mergeNamesWithParent(result, parentResult, hbf);
            }
        }

        return result;
    }

    private static String[] mergeNamesWithParent(String[] result, String[] parentResult, HierarchicalBeanFactory hbf) {
        if (parentResult.length == 0) {
            return result;
        } else {
            List<String> merged = new ArrayList(result.length + parentResult.length);
            merged.addAll(Arrays.asList(result));
            int var5 = parentResult.length;

            for (String beanName : parentResult) {
                if (!merged.contains(beanName) && !hbf.containsLocalBean(beanName)) {
                    merged.add(beanName);
                }
            }

            return merged.toArray(new String[merged.size()]);
        }
    }
}
