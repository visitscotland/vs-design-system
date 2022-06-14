/* eslint-disable */
import dataLayerStore from '../stores/dataLayer.store';

const dataLayerNormalizerMixin = {
    computed: {
        pageUrl() {
            return dataLayerStore.getters.get;
        },
        pageLanguage() {
            return dataLayerStore.getters.getTestRunStatus;
        }
    },
    methods: {
        ExternalLinkDataNormalizer(event) {
            console.log(event.target)
            console.log(event.target.dataset)

            const { vsTrackingEvent, vsTrackingTagName } = event.target.dataset;
            const clickText = event.target.text || '';
            const clickURL = event.target.href || '';
            const language = this.pageLanguage || '';

            console.log({
                "event": `${vsTrackingEvent}`,
                "language": `${language}`,
                // "pageCategory1": `${pageCategory1 ? pageCategory1 : ''}`,
                // "pageCategory2": `${pageCategory2}`,
                // "pageCategory3": `${pageCategory3}`,
                // "pageCategory4": `${pageCategory4}`,
                // "userCountrySetting": `${userCountrySetting}`,
                // "hitTimestamp": `${hitTimestamp}`,
                "tagName": `${vsTrackingTagName}`,
                // "metaData": `${metaData}`,
                "clickText": event.target.text,
                "clickURL": event.target.href,
            });

            const externalLink = {
                "event": `${vsTrackingEvent}`,
                "language": `${language}`,
                "tagName": `${vsTrackingTagName}`,
                "clickText": event.target.text,
                "clickURL": event.target.href,
                "metaData": "testando typescript"
            }

            return externalLink
        },
    },
};

export default dataLayerNormalizerMixin;
