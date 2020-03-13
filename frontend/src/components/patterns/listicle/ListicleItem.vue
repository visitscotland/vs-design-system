<template>
    <li class="vs-listicle-item border">
        
			<slot name="hippo-details" />

			<!-- HEADER -->
            <div class="border-bottom border-white d-flex justify-content-start align-items-top">
                <div class="position-relative">
                    <div class="count__bg">
                    	<span class="count" aria-hidden="true">{{ index }}</span>
                    </div>
                </div>
                <vs-heading level="3">
                    {{ title }}

                    <template slot="sub-heading">
                        {{subTitle}}
                    </template>
                </vs-heading>
            </div>

			<!-- BODY -->
			<div>
				<!-- @slot Contains the image to be shown.  -->
				<slot name="image-slot" />

				<div class="content">
					
					<div class="intro">
						<!-- @slot Contains the description to be shown.  -->
						<slot name="description-slot" />
					</div>

					<!-- @slot Contains the facilities list to be shown.  -->
					<slot name="facilities-slot" class="facilities" />

				</div>
			</div>
    </li>
</template>

<script>
import VsHeading from "@components/elements/heading/Heading"
import VsDescriptionList from "@components/elements/description-list/DescriptionList"

export default {
    name: "VsListicleItem",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsHeading
	},
    props: {
		/**
		 * The index value for the listicle item to be shown on the header
		 */
    	index: {
    		type: String
		},
		/**
		 * The listicle item title
		 */
    	title: {
    		type: String
		},
		/**
		 * The listicle item subtitle
		 */
		subTitle: {
    		type: String
		}
    }
}
</script>

<style lang="scss" scoped>

.vs-listicle-item {
    margin: $spacer-11 0;
    
    .count {
        color: $color-white;
        font-family: $headings-font-family;
        font-size: $display1-size;
        line-height: 1.4;
        display: block;
        text-align: center;
        width: 100%;
        border-bottom: 1px solid $color-white;
    }

    .count__bg {
        background: $color_secondary_teal;
        padding: $spacer-3 $spacer-7;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    &.border {
        padding: $spacer-4;

        @include media-breakpoint-up(md) {
            padding: spacer-8;
        }

        @include media-breakpoint-up(xl) {
            padding: $spacer-11;
        }
    }

    h3.heading {
        display: flex;
        flex-direction: column;
        justify-content: center;
        margin-left: $spacer-2;
        margin-bottom: $spacer-8;

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
            overflow-wrap: break-word;
            margin-bottom: $spacer-4;
            
            @include media-breakpoint-up(md) {
                margin-right: $spacer-4;
            }
        }

        a.cta {
            display: block;
        }

        .facilities {
            border-top: 1px solid $color-gray-tint-5;


            @include media-breakpoint-down(xs) {
                min-width: calc(100% + 2rem);
                margin-left: -1rem;
            }

            @include media-breakpoint-up(md) {
                border: initial;
                border-left: 1px solid $color-gray-tint-5;
                margin-top: 0;
                padding: $spacer-8;
                width: auto;
            }

            .vs-icon-description-list {
                font-size: $h6-font-size;
                line-height: 16px;
                border: 0;
                display: grid;
                width: max-content;
                grid-template-columns: 1fr 1fr 1fr;
                margin: 0 auto;
                align-self: center;
                justify-self: center;

                @include media-breakpoint-up(sm) {
                    padding: 2rem 0;
                }

                @include media-breakpoint-up(md) {
                    padding: 0;
                }

                & ::v-deep dd {
                    justify-self: center;
                    padding: 0 $spacer-4;
                    display: inline-block;
                }
            }
        }
    }

}
</style>

<docs>
```jsx

	<ul style="list-style-type: none; padding: 0;">
		<vs-listicle-item 
			v-for="(item, index) in listicles.sampleListicle"
			key="index"
			index="1"
			:title="item.title"
			:subTitle="item.subTitle"
			ctaLink=""
			:ctaLabel="item.ctaLabel"
		>
			<div slot="image-slot">
				<vs-image-with-caption
					:altText="item.image.altText"
					:image-src="item.image.imageSrc"
					:latitude="item.image.latitude"
					:longitude="item.image.longitude"
					variant="large"
				>
					<vs-img 
						class="lazyload" 
						:src="item.image.imageSrc"
						srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
						:data-srcset="item.image.imageSrc" 
						:alt="item.image.altText"
						data-sizes="auto">
					</vs-img>

					<span slot="caption">
						{{ item.image.caption }}
					</span>

					<span slot="credit">
						&copy; {{ item.image.credit }}
					</span>
				</vs-image-with-caption>
			</div>

			<div slot="description-slot">
				<p data-v-0abaabb3="">The&nbsp;<strong data-v-0abaabb3="">National Museum of Scotland</strong>&nbsp;in&nbsp;<a data-v-0abaabb3="" href="https://en.wikipedia.org/wiki/Edinburgh" title="Edinburgh" target="_blank">Edinburgh</a>, Scotland, was formed in 2006 with the merger of the new&nbsp;<strong data-v-0abaabb3="">Museum of Scotland</strong>, with collections relating to Scottish&nbsp;<a data-v-0abaabb3="" href="https://en.wikipedia.org/wiki/Antiquities" title="Antiquities" target="_blank">antiquities</a>,&nbsp;<a data-v-0abaabb3="" href="https://en.wikipedia.org/wiki/Culture_of_Scotland" title="Culture of Scotland" target="_blank">culture</a>&nbsp;and&nbsp;<a data-v-0abaabb3="" href="https://en.wikipedia.org/wiki/History_of_Scotland" title="History of Scotland" target="_blank">history</a>, and the adjacent&nbsp;<strong data-v-0abaabb3="">Royal Scottish Museum</strong>&nbsp;(so renamed in 1904), with collections covering science and technology,&nbsp;<a data-v-0abaabb3="" href="https://en.wikipedia.org/wiki/Natural_history" title="Natural history" target="_blank">natural history</a>, and world cultures. The two connected buildings stand beside each other on&nbsp;<a data-v-0abaabb3="" href="https://en.wikipedia.org/wiki/Chambers_Street_(Edinburgh)" title="Chambers Street (Edinburgh)" target="_blank">Chambers Street</a>, by the intersection with the&nbsp;<a data-v-0abaabb3="" href="https://en.wikipedia.org/wiki/George_IV_Bridge" title="George IV Bridge" target="_blank">George IV Bridge</a>, in central Edinburgh. The museum is part of&nbsp;<a data-v-0abaabb3="" href="https://en.wikipedia.org/wiki/National_Museums_Scotland" title="National Museums Scotland" target="_blank">National Museums Scotland</a>. Admission is free.</p>
			</div>

			<div slot="facilities-slot" class="facilities">
				<vs-icon-description-list v-if="item.facilities.length">
                    <vs-icon-description-list-detail 
                        v-for="(facility, facilitiesIndex) in item.facilities"
                        :key="facilitiesIndex"
                        :label="facility.value"
                        :icon="facility.key"
                        />
                </vs-icon-description-list>
			</div>
		</vs-listicle-item">
	</ul>
```
</docs>