<template>
	<div class="vs-facilities-list" >
		<vs-icon-description-list
			v-if="facilities.length"
		>
			<vs-icon-description-list-term v-if="title">{{title}}</vs-icon-description-list-term>
			<vs-icon-description-list-detail 
				v-for="(facility, facilitiesIndex) in facilities"
				:key="facilitiesIndex"
				:label="facility.value"
				:icon="facility.key"
			/>
		</vs-icon-description-list>
	</div>
</template>

<script>
import VsIconDescriptionList from "@components/patterns/icon-description-list/IconDescriptionList"

export default {
	props: {
		/**
		 * Array of facilities
		 */
		facilities: {
			type: Array,
		},
		/**
		 * Array of facilities
		 */
		title: {
			type: String,
		},
	}
}
</script>

<style lang="scss" scoped>
.vs-facilities-list {
	font-size: 14px;
	line-height: 16px;

	.vs-icon-description-list {
		border: 0;
		display: grid;
		width: max-content;
		grid-template-columns: 1fr 1fr 1fr;
		margin: 0 auto;
		padding: 0;
		align-self: center;
		justify-self: center;

		& > * {
			justify-self: center;
		}
	}

}
</style>