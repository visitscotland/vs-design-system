/* eslint-disable */
const dataLayerPushTemplatesMixin = {
    methods: {
        ExternalLinkPushTemplate({
                event = '',
                language = '',
                pageCategory1 = '',
                pageCategory2 = '',
                pageCategory3 = '',
                pageCategory4 = '',
                userCountrySetting = '',
                hitTimestamp = '',
                tagName = '',
                metaData = '',
                clickText = '',
                clickURL = ''
        }) {
            // dataLayer.push({})
            console.log({
                "event": `${event}`,
                "language": `${language}`,
                "pageCategory1": `${pageCategory1}`,
                "pageCategory2": `${pageCategory2}`,
                "pageCategory3": `${pageCategory3}`,
                "pageCategory4": `${pageCategory4}`,
                "userCountrySetting": `${userCountrySetting}`,
                "hitTimestamp": `${hitTimestamp}`,
                "tagName": `${tagName}`,
                "metaData": `${metaData}`,
                "clickText": `${clickText}`,
                "clickURL": `${clickURL}`,
            });
        },
    },
};

export default dataLayerPushTemplatesMixin;
