<template>
<div>
    <DSHeader/>
  <div class="container">
    <DSSidebar/>
    <div class="content">
      <Nuxt />
    </div>
  </div>
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
};
</script>
