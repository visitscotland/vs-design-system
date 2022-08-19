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
        removeAttrs() {
            const attrs = Object.keys(this.$attrs);

            attrs.forEach((attr) => {
                if (!this.checkIgnoreList(attr)) {
                    this.$el.removeAttribute(attr);
                }
            });
        },
        checkIgnoreList(attr) {
            const ignoreList = [/data-/, /aria-/];
            return ignoreList.some((el) => el.test(attr));
        },
    },
};

export default removeAttrsMixin;
