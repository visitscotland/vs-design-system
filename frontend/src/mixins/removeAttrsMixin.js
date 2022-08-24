/*
* Anywhere we use v-bind="$attrs" to pass props to a
* component, we must use this mixin to remove the attributes
* from the original element to pass W3C HTML Validation.
*/

const removeAttrsMixin = {
    mounted() {
        this.removeAttrs();
    },
    methods: {
        /**
         * Removes the attributes from the element if
         * not on ignore list
         */
        removeAttrs() {
            const attrs = Object.keys(this.$attrs);

            attrs.forEach((attr) => {
                if (!this.checkIgnoreList(attr)) {
                    this.$el.removeAttribute(attr);
                }
            });
        },
        /**
         * Tests whether any items in the array contain any
         * string in the ignore list, returns a boolean
         */
        checkIgnoreList(attr) {
            const ignoreList = [/data-/, /aria-/, /disabled/, /srcset/, /sizes/];
            return ignoreList.some((el) => el.test(attr));
        },
    },
};

export default removeAttrsMixin;
