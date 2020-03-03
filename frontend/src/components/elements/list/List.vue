<template>
	<div
		class="vs-list"
		:class="variant"
	>
		<ul v-if="type === 'ul'">
			<slot/>
		</ul>

		<ol v-else-if="type === 'ol'">
			<slot/>
		</ol>
	</div>
</template>

<script>
import Vue from 'vue';

window.Vue = Vue;

/**
 * Generic List Component that can be used for any kind of list needed given the right properties.
 * 
 */
export default {
	name: "VsList",
    status: "prototype",
    release: "0.0.1",
	props: {
        /**
         * Option to choose the kind of list needed
         * `ul, ol`
         */
        type: {
            type: String,
			default: "ul",
            validator: value => {
                return value.match(/(ul|ol)/)
            },
		},
		/**
         * Option to choose the style of list needed, more than one can be chosen
         * `unstyled, inline`
         */
        variant: {
			type: String,
			validator: value => {
                return value.match(/(unstyled|inline)/)
            },
        },
	},
}
</script>

<style lang="scss" scoped>
	.vs-list {
		
		&.unstyled > ul,
		&.unstyled > li {
			list-style: none;
		}

		&.inline > ul,
		&.inline > li {
			display: flex;
		}
	}
</style>

<docs>
```jsx
	<h3>Unstyled Inline</h3>
	<vs-list type="ul" variant="unstyled inline">
		<li class="mr-7">Test</li>
		<li class="mr-7">Test</li>
		<li class="mr-7">Test</li>
	</vs-list>

	<h3>Inline</h3>
	<vs-list type="ul" variant="inline">
		<li class="mr-7">Test</li>
		<li class="mr-7">Test</li>
		<li class="mr-7">Test</li>
	</vs-list>

	<h3>Unstyled</h3>
	<vs-list type="ul" variant="unstyled">
		<li class="mr-7">Test</li>
		<li class="mr-7">Test</li>
		<li class="mr-7">Test</li>
	</vs-list>

	<h3>Unordered List</h3>
	<vs-list type="ul" >
		<li>Test</li>
	</vs-list>

	<h3>Ordered List</h3>
	<vs-list type="ol" >
		<li>Test</li>
	</vs-list>
```
</docs>