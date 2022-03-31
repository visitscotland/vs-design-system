<template>
    <div class="sidebar">
        <nav>
            <ul>
                <li v-for="section of sections" :key="section.path">
                    <NuxtLink :to="section.path">
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
  async asyncData({ $content, params }) {
    const sections = await $content({deep: true})
      .only(['title', 'description', 'img', 'slug', 'author'])
      .sortBy('createdAt', 'desc')
      .fetch()

      return {
        sections
      }
  },
  data() {
    return {
      sections: []
    }
  },
};
</script>
<style scoped lang="scss">
.sidebar {
    width: 25%;
    padding-right: 64px;
    height: 100vh;

    ul li a {
        font-size: 24px;
        text-decoration: none;
        padding: 4px 16px;
    }
}
</style>
