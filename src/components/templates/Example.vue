<template>
  <component :is="type" class="index">
    <NavBar
      active="Index"
      :navItems="[
        { name: 'Template', component: 'Index', href: '/#/' },
        { name: 'Documentation', href: 'http://localhost:6060/' },
      ]"
    />
    <Wrapper>
      <Heading>Vue Design System</Heading>
      <Paragraph>
        <a href="https://vueds.com">Vue Design System</a>is an open-source tool for building design
        systems with Vue.js. It provides you and your team a set of organized tools, patterns &amp;
        practices. It works as the foundation for your application development.
      </Paragraph>
    </Wrapper>
  </component>
</template>

<script>
/**
 * The default page template.
 */
export default {
  name: "VsDefault",
  status: "prototype",
  release: "0.0.1",
  metaInfo: {
    title: "VisitScotland Design System",
    htmlAttrs: {
      lang: "en",
    },
  },
  props: {
    /**
     * The html element name used for the component.
     */
    type: {
      type: String,
      default: "div",
    },
  },
}
</script>

<style lang="scss" scoped></style>

<docs>
  ```jsx
  <Default />
  ```
</docs>
