/* eslint-disable */
import { Store } from 'vuex';
import dataLayerStore from '../stores/dataLayer.store';
import { externalLinkTemplate } from '../utils/data-layer-templates';

/**
 * There is a general function to retrieve any value from the store:
 * "field_needed": dataLayerStore.getters.getValueFromKey("field_needed"),
 * example: "page_category_1": dataLayerStore.getters.getValueFromKey("page_category_1")
 */

const dataLayerMixin = {
    // Computed functions can be used to retrieve data from the page store
    computed: {
        // Retrieving page url from the page store created by:
        // dataLayer.store.js (Central Store)
        // TagManagerWrapper.vue (Global component that reads and updates the store)
        pageUrl() {
            return dataLayerStore.getters.getPageUrl;
        },
        pageLanguage() {
            return dataLayerStore.getters.getTestRunStatus;
        },
    },
    methods: {
        // This function matches values passed as an object
        // To the template listed on data-layer-template.js
        // And return any value not matched as undefined
        templateFiller(template, values) {
            // Create an empty object
            const obj = {
            };
            
            // Check every element on the template
            template.forEach((element) => {
                // Push the key from the template to the object created before
                obj.push = element;

                // If there is a key on the object passed by the user that matches a key from the template
                // Then use the user given value otherwise return it as undefined
                // This also ignores any key passed by the user that doesn't match a key from the template
                obj[element] = values[element] || undefined;
            });
            
            // Return an object ready to be pushed to the data-layer
            return obj;
        },
        pageViewTemplateDataEvent(event) {
            const eventName = "page_view"
            const tagName = "VS - GA - Pageview"
        },
        menuNavigationDataEvent(event) {
            const eventName = "menu_navigation"
            const tagName = "VS - GA - Mega Menu"
        },
        newsletterDataEvent(event) {
            const eventName = "newsletter"
            const tagName = "VS - GA - Newsletter"
        },
        shareDataEvent(event) {
            const eventName = "share"
            const tagName = "VS - GA - Share"
        },
        socialMediaExternalLinkDataEvent(event) {
            const eventName = "social_media_external_link"
            const tagName = "VS - GA - Social Media External Link"
        },
        homePageLogoClickDataEvent(event) {
            const eventName = "homepage_logo_click"
            const tagName = "VS - GA - Homepage Logo Click"
        },
        videoTrackingDataEvent(event) {
            const eventName = "video_tracking"
            const tagName = "VS - GA - Video Tracking"
        },
        externalLinkDataEvent(event) {
            // Fixed values
            const eventName = "external_link"
            const tagName = "VS - GA - External Link"

            // Values that come both from the component that called the function 
            // As well as from the page store
            // Left column needs to match the template names from data-layer-templates.js
            const templateValues = {
                "event": eventName,
                "language": this.pageLanguage,
                "tag_name": tagName,
                "click_text": event.target.text,
                "click_URL": event.target.href,
                "page_category_1": dataLayerStore.getters.getValueFromKey("page_category_1"),
                "page_category_2": dataLayerStore.getters.getValueFromKey("page_category_2"),
                "page_category_3": dataLayerStore.getters.getValueFromKey("page_category_3"),
                "user_country_setting": dataLayerStore.getters.getValueFromKey("user_country_setting"),
                "meta_data": dataLayerStore.getters.getValueFromKey("meta_data")
            }

            // Running the values and the template trough the templateFiller() function
            // This will make sure that the values are added on the right place
            // And if any value was not found then it will return as undefined (as per iProspect request)
            const externalLink = this.templateFiller(externalLinkTemplate, templateValues);
            
            // After that we just need to push the object returned to the data layer
            // datalayer.push(externalLink);
            console.log(`externalLink:`)
            console.log(externalLink)
        },
        internalLinkDataEvent(event) {
            const eventName = "internal_link"
            const tagName = "VS - GA - Internal Link"

            const templateValues = {
                "event": eventName,
                "language": this.pageLanguage,
                "tag_name": tagName,
                "click_text": event.target.text,
                "click_URL": event.target.href,
            }

            const internalLink = this.templateFiller(externalLinkTemplate, templateValues);
            // datalayer.push(externalLink);

            console.log(`internalLink:`)
            console.log(internalLink)
        },
        internalNavigation(event) {
            const eventName = "internal_navigation"
            const tagName = "VS - GA - Internal Navigation"
        },
        errorDataTemplate(event) {
            const eventName = "errors"
            const tagName = "VS - GA - Errors"
        },
        mapInteractionDataTemplate(event) {
            const eventName = "map_interaction"
            const tagName = "VS - GA - Map Interaction"
        },
        cannedSearchDataTemplate(event) {
            const eventName = "canned_search"
            const tagName = "VS - GA - Canned Search"
        },
        formsDataTemplate(event) {
            const eventName = "forms"
            const tagName = "VS - GA - Forms"
        }
    },
};

export default dataLayerMixin;
