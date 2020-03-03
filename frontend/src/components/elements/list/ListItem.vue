<template>
  <li>
      <slot/>
  </li>
</template>

<script>
/**
 * List Item wrapper component, wraps the content into a list item element
*/
export default {
    name: "VsListItem",
    status: "prototype",
    release: "0.0.1",
}
</script>

<docs>
```jsx
    <ul>
        <vs-list-item>
            Item
        </vs-list-item>
        <vs-list-item>
            Item
        </vs-list-item>
        <vs-list-item>
            Item
        </vs-list-item>
    </ul>

    <ol>
        <vs-list-item>
            Item
        </vs-list-item>
        <vs-list-item>
            Item
        </vs-list-item>
        <vs-list-item>
            Item
        </vs-list-item>
    </ol>
```
</docs>