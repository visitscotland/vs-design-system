const {
    get,
    map,
    zipObject,
    partial,
    startsWith,
    groupBy,
    mapValues,
    isArray,
    includes,
    castArray,
    omitBy,
} = require("lodash")

/**
 * This module prepares moduleMap for the ssr-dynamic-component-loader
 * 
 * @param {Object} moduleMap 
 * @param {String|[String]} exclude 
 */

const getType = (moduleName) => {

    if (startsWith(moduleName, "store")) {
        return "stores"
    }

    if (moduleName === "core") {
        return "core"
    }

    return "components"
};

const prepareModule = (moduleValue, moduleName) => {
    const type = getType(moduleName);
    const key = type === "components" ? `Vs${moduleName}` : moduleName;

    return {
        key,
        type,
        value: moduleValue
    }
}

const getIncludedModules = (moduleMap, exclude) => {
    const excludeModuleWithName = partial(
        includes, 
        castArray(exclude),
        partial.placeholder,
        0
    )

    if(exclude) {
        return omitBy(moduleMap, (moduleBody, moduleName) => {
            return excludeModuleWithName(moduleName);
        });
    }
    
    return clone(moduleMap);
}

module.exports = (moduleMap, exclude) => {
    const includedModules = getIncludedModules(moduleMap, exclude);

    const groupedModules = groupBy(
        map(includedModules, prepareModule),
        "type"
    );

    return mapValues(groupedModules, (items, groupName) => {
        if (groupName === "core") {
            return isArray(items) && items.length ? get(items[0], "value") : null;
        }

        return zipObject(map(items, "key"), map(items, "value"));
    });
}
