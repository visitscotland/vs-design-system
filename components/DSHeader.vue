<template>
    <header>
        <div class="logo-wrapper">
            <a href="/">
                <img
                    src="~/assets/images/ds-logo-png.png"
                    alt="Design System Logo"
                >
            </a>
        </div>
    </header>
</template>
<script>
export default {
    name: 'DSHeader',
    status: 'prototype',
};
</script>
<style scoped lang="scss">
header {
  margin-bottom: 2rem;
  padding: 24px 64px;
  border-top: 10px solid #700e57;
  box-shadow: #700e57 0px 1px 2px 0px;

  .logo-wrapper {
    width: 230px;

    a {
      display: block;

      img {
        width: 230px;
      }
    }
  }
}
</style>
