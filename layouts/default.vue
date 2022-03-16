<template>
    <div>
        <DSHeader />
        <div class="content">
            <DSSidebar />
            <Nuxt />
        </div>
    </div>
</template>
<style lang="scss">

body {
    font-family: "Source Sans Pro",Roboto,"Helvetica Neue",Arial,sans-serif;
    margin:0;
    padding:0;
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
