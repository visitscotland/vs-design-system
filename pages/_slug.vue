<template>
    <div>
        <h2>{{ section.title }}</h2>
        <NuxtContent :document="section" />
    </div>
</template>

<script>
export default {
    async asyncData({ $content, params, error }) {
        let section;
        try {
            section = await $content('sections', params.slug).fetch();
            // OR const article = await $content(`articles/${params.slug}`).fetch()
        } catch (e) {
            error({
                message: 'Blog Post not found',
            });
        }

        return {
            section,
        };
    },
};
</script>
