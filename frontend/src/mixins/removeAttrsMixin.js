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
            Object.keys(this.$attrs).forEach((attr) => {
                this.$el.removeAttribute(attr);
            });
        },
    },
};

export default removeAttrsMixin;
