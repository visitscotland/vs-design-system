<template>
	<div
		class="vs-rich-text-wrapper"
        :class="[variant]"
	>
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
		props: {
			/**
			 * The font size used by the wrapper
			 * `normal | lead`
			 */
			variant: {
				type: String,
				default: "normal",
				validator: value => {
					return value.match(/(normal|lead)/)
				},
			}
		}
	}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";

.normal ::v-deep {
	font-family: $font-family-base;
	font-size: $font-size-base;
}

.lead ::v-deep {
	line-height: 1.75rem;
}
</style>

<docs>
```jsx
	<bs-wrapper class="mb-9">
		<h3>Normal Variant</h3>
		<vs-rich-text-wrapper>
			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis <a href="#foo">eget</a> ante urna.<br/> Pellentesque aliquam faucibus enim fermentum fringilla. Vivamus ultrices dictum justo ac porta.
			Quisque mattis <b>tortor</b> dapibus tellus aliquet, finibus lacinia felis pulvinar.
		</vs-rich-text-wrapper>
    </bs-wrapper>

	<bs-wrapper class="mb-4">
		<h3>Lead Variant</h3>
		<vs-rich-text-wrapper variant="lead">
			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis <a href="#foo">eget</a> ante urna.<br/> Pellentesque aliquam faucibus enim fermentum fringilla. Vivamus ultrices dictum justo ac porta.
			Quisque mattis <b>tortor</b> dapibus tellus aliquet, finibus lacinia felis pulvinar.
		</vs-rich-text-wrapper>
    </bs-wrapper>
```
</docs>