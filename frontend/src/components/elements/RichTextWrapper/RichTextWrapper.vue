<template>
	<div class="vs-rich-text-wrapper">
		<slot/>
	</div>
</template>

<script>
	/**
	 * Text Wrapper is used to wrap and render HTML or text strings from WYSIWYG editors or others and apply styles when needed.
	*/
	export default {
		name: "VsRichTextWrapper",
		status: "prototype",
		release: "0.0.1",
	}
</script>

<style>
.vs-rich-text-wrapper {

}
</style>

<docs>
```jsx
	<vs-rich-text-wrapper>
		<h3>Heading Example</h3>
			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis <a href="#foo">eget</a> ante urna.<br> Pellentesque aliquam faucibus enim fermentum fringilla. Vivamus ultrices dictum justo ac porta.
			Quisque mattis <b>tortor</b> dapibus tellus aliquet, finibus lacinia felis pulvinar.</br>
	</vs-rich-text-wrapper>
```
</docs>