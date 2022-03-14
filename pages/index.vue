<template>
  <div class="page-index">
Hello
  </div>
</template>

<style>
body {
    font-family: Source Sans Pro, Helvetica, Arial, sans-serif, "Oxygen", "Ubuntu", "Cantarell", "Fira Sans", "Droid Sans", "Helvetica Neue", sans-serif;
}
.page-index {
  padding-top: 60px;
  text-align: center;
}
</style>
<script>
export default {
    head() {
        return {
            script: [{ src: 'https://identity.netlify.com/v1/netlify-identity-widget.js' }],
        };
    }
}
</script>