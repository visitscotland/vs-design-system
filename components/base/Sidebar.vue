<template>
  <ul>
    <li v-for="section of sections" :key="section.slug">
      <NuxtLink :to="section.slug">{{ section.title }}</NuxtLink>
    </li>
  </ul>
</template>

<script>
export default {
   async asyncData({ $content }) {
    const sections = await $content("design-system").fetch();

    return {
      sections,
    };
  },
};
</script>