<template>
    <div class="content">
        Content
    </div>
</template>
<script>
export default {
    head() {
        return {
            script: [{
                src: 'https://identity.netlify.com/v1/netlify-identity-widget.js',
            }],
            link: [{
                rel: 'stylesheet',
                href: 'https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,600,700',
            }],
        };
    },
};
</script>
