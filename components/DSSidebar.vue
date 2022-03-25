<template>
    <div class="sidebar">
        <nav>
            <ul>
                <li
                    v-for="section of sections"
                    :key="section.slug"
                >
                    <NuxtLink :to="section.slug">
                        {{ section.title }}
                    </NuxtLink>
                </li>
            </ul>
        </nav>
    </div>
</template>
<script>

export default {
    name: 'DSSidebar',
    async fetch() {
        this.sections = await this.$content('sections').fetch();
    },
    data() {
        return {
            sections: [],
        };
    },
    status: 'prototype',
};
</script>
<style scoped lang="scss">
.sidebar {
  width:25%;
  padding-right: 64px;
  height: 100vh;

  ul li a {
    font-size: 24px;
    text-decoration: none;
    padding: 4px 16px;
  }

}
</style>
