<template>
<div>
  HEllo
    <DSHeader/>
    <DSSidebar :links=sections />
      <Nuxt />
</div>
</template>
<style lang="scss">

body {
    font-family: "Source Sans Pro",Roboto,"Helvetica Neue",Arial,sans-serif;
    margin:0;
    padding:0;
}
.container {
  display: flex;
  flex-direction: row;
}

.content {
  display: flex;
}

ul {
  list-style: none;
}

a {
  color: #191919;
}

h1, h2 {
  font-weight: 400;
  margin: 0;
  padding: 0;
  text-transform: uppercase;
  font-family: "evelethclean-regular", Helvetica, Arial, sans-serif
}
</style>
<script>
export default {
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
