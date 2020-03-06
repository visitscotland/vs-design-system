<template>
    <li class="vs-listicle-item">
        <div class="border">

			<!-- HEADER -->
            <div class="d-flex justify-content-start align-items-top">
				<!-- This could be a Card Header component -->
                <div class="position-relative">
                    <div
						:class="{ count__bg: !icon }"
					>
						<vs-icon v-if="icon"
							:name="icon"
							variant="secondary-teal"
							size="md"
							:padding="0"
						/>
                    	<span class="count" aria-hidden="true">{{ index }}</span>
                    </div>
                </div>
                <vs-heading level="3" thin class="heading">
                    <span>{{ name }}</span>
                    {{ place }}
                </vs-heading>
            </div>

			<!-- BODY -->
			<div>
				<vs-image-with-caption v-bind="image">
					<img 
						class="lazyload" 
						:src="image.imageSrc"
						srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
						:data-srcset="image.imageSrc" 
						:alt="image.altText"
						data-sizes="auto"
					/>
				</vs-image-with-caption>

				<div class="content">
					
					<div class="intro">
						<p v-html="intro"></p>

						<vs-link
							v-for="link in links"
							:key="link.url"
							:href="link.url"
							class="cta"
						>
							{{ link.text }}
						</vs-link>
					</div>

					<vs-facilities-list 
						v-bind:facilities="facilitiesList"
						class="facilities"
					/>
				</div>
			</div>
        </div>
    </li>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import VsHeading from "@components/elements/heading/Heading"
import VsDescriptionList from "@components/elements/description-list/DescriptionList"
import VsFacilitiesList from "@components/elements/facilities-list/FacilitiesList"

export default {
    name: "VsListicleItem",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsHeading,
		VsIcon,
		VsFacilitiesList
    },
    props: {
        /**
         * Label used for the word 'Stop'
         */
        icon: {
            type: String,
        },
        intro: {
            type: String
		},
		index: {
			type: Number
		},
		name: {
			type: String
		},
		place: {
			type: String
		},
		image: {
			type: Object
		},
		facilitiesList: {
			type: Array
		},
		links: {
			type: Array
		}
    }
}
</script>

<style lang="scss" scoped>

.count {
	color: $color-white;
	font-family: $headings-font-family;
	font-size: 30px;
	display: block;
	text-align: center;
	width: 100%;
	border-bottom: 2px solid white;

	&__bg {
		background: $color_secondary_teal;
		padding: 1rem 2rem;
		display: flex;
		align-items: center;
		justify-content: center;
	}
}

.border {
	padding: 2rem;

	@include media-breakpoint-up(xl) {
		padding: 5rem;
	}
}

h3.heading {
	display: flex;
	flex-direction: column;
	justify-content: center;
	margin-left: .5rem;
	margin-bottom: 2rem;

	@include media-breakpoint-up(md) {
		margin-bottom: 0;
	}
}

.content {
	display: flex;
	flex-direction: column;

	@include media-breakpoint-up(md) {
		flex-direction: initial;
	}

	.intro {
		margin-right: 1rem;
	}

	a.cta {
		display: block;
	}

	.facilities {
		border-top: 1px solid $color-gray-tint-5;
		margin-top: 2rem;
		padding: 2rem 0 0;
		width: 100%;

		@include media-breakpoint-down(xs) {
			min-width: calc(100% + 4rem);
			margin-left: -2rem;
		}

		@include media-breakpoint-up(md) {
			border: initial;
			border-left: 1px solid $color-gray-tint-5;
			margin-top: 0;
			padding: 2rem;
			width: auto;
		}

	}
}

</style>

<docs>
```jsx
	const data = {
		index: 1,
        name: "The Standing Stones of Stenness",
        place: "Orkney Islands",
        "image": {
			source: "image",
			"imageSrc": "fixtures/itineraries/images/elie-beach-header.jpg",
			"altText": "Child playing on Elie Beach",
			"caption": "Elie beach",
			"credit": "Test Credit",
			"longitude": "-2.8243733",
			"latitude": "56.1896033"
		},
        intro: `
            See how the Viking fir experienced rain<br/><br/>

            This extraordinary concentration of monuments that includes rock carvings, standing stones,
            and Neolithic and Bronze Age burial cairns, distinguishes the Kilmartin Valley as Scotland's
            richest pre historic landscape.
        `,
        links: [
			{
            	url: "#link1",
				text: "link1"
			},
			{
            	url: "#link2",
				text: "link2"
			}
		],
        facilitiesList: [
            {
                "key": "parking",
                "value": "Parking"
            },
            {
                "key": "accessparkdrop",
                "value": "Accessible Parking and Dropoff"
            },
			{
				"key": "dsblaccess",
				"value": "Disabled Access"
			},
			{
				"key": "facility-petswelcom",
				"value": "Pets Welcome"
			}
        ]
    }

	<ul style="list-style-type: none; padding: 0;">
		<vs-listicle-item v-bind="data" />
	</ul>
```
</docs>