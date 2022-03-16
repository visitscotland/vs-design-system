<template>
    <header class="page-header">
        <h1>{{ title }}</h1>
        <nav>
            <a href="https://github.com/visitscotland">Github</a>
        </nav>
    </header>
</template>
<style lang="scss" scoped>
    header.page-header {
        padding-top: 60px;
        text-align: center;
        border-bottom: 2px solid #eee;
    }
</style>
<script>
export default {
    name: 'DSHeader',
    props: {
        title: {
            type: String,
            default: 'VisitScotland Design System'
        }
    }
}
</script>