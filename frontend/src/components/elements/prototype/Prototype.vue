<template>
	<div class="vs-prototype p-0 m-0">
		<slot />
	</div>
</template>

<script>
	/**
	 * Prototypes are wrappers used to visually communicate parts
	 * of the product or actions that are still unavailable.
	 * 
	 * This is a development only tool to help visualise
	 * components/functionalities to be finished.
	 */

	export default { 
		name: "VsPrototype",
		status: "prototype",
		release: "0.0.1",
	}
</script>

<style lang="scss" scoped>
.vs-prototype {
	opacity: 30%;

	& > * {
		color: darken(grey, 70%);
		border-color: darken(grey, 70%);
		background: lighten(grey, 30%);
	}

	:hover,
	&:hover {
		cursor: not-allowed;
	}
}
</style>

<docs>
```jsx
    <h4>Originals</h4>
	<bs-wrapper class="d-flex mb-4">
		<vs-button class="mr-2 mb-2">Button</vs-button>		
    </bs-wrapper>

	<bs-wrapper class="d-flex mb-4">
		<vs-icon name="search" />
    </bs-wrapper>

    <h4>Prototypes</h4>
	<bs-wrapper class="d-flex mb-4">
		<vs-prototype>
      		<vs-button class="mr-2 mb-2">Button</vs-button>		
		</vs-prototype>
    </bs-wrapper>

	<bs-wrapper class="d-flex mb-4">
		<vs-prototype>
			<vs-icon name="search" />
		</vs-prototype>
    </bs-wrapper>
```
</docs>