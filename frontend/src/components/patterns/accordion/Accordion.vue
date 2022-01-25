<template>
    <div
        class="vs-accordion"
        data-test="vs-accordion"
    >
        <!-- @slot Default slot to contain a list of *AccordionItem* components -->
        <slot />
    </div>
</template>

<script>

/**
 * The accordion component is a list of stacked items that show or
 * hide content on the page.
 *
 * @displayName Accordion
 */

export default {
    name: 'VsAccordion',
    props: {
        /**
         * If this is provided, the accordion expands above
         * the specified viewport `xs, sm, md, lg, xl, xxl`
         */
        breakPoint: {
            type: String,
            default: null,
            validator: (value) => value.match(/(xs|sm|md|lg|xl|xxl)/),
        },
    },
    /**
     * Provides breakPoint prop to be injected to child component AccordionItem
     */
    provide() {
        return {
            breakPoint: this.breakPoint,
        };
    },
};
</script>

<style lang="scss">
</style>

<docs>
## Usage
The accordion component displays a list of
items that can be toggled to show hidden content panels. When one item is clicked,
the item will expand an associated panel to reveal its contents. When clicked again, the panel
will be hidden once again. Multiple items can be open at the same time.

### Standard

Used with a list of *AccordionItem* components, each item has the option
to be open by default by passing in the `openByDefault` prop.
The button style can also be changed by passing in the button
`variant` to the item.

  ```js
    <VsAccordion>
        <VsAccordionItem
            :open-by-default="true"
            variant="transparent"
            control-id="accordion_item_1"
        >
            <span slot="title">
                <VsIcon
                    name="walk"
                    variant="dark"
                    size="sm"
                    class="mr-2"
                />
                Walking
            </span>

            <VsIcon
                name="chevron"
                variant="dark"
                size="sm"
                slot="icon-open"
            />

            <VsIcon
                name="chevron"
                orientation="down"
                variant="dark"
                size="sm"
                slot="icon-closed"
            />

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </VsAccordionItem>

        <VsAccordionItem
            :open-by-default="false"
            variant="transparent"
            control-id="accordion_item_2"
        >
            <span slot="title">
                <VsIcon
                    name="car"
                    variant="dark"
                    size="sm"
                    class="mr-2"
                />
                Driving
            </span>

            <VsIcon
                name="chevron"
                variant="dark"
                size="sm"
                slot="icon-open"
            />

            <VsIcon
                name="chevron"
                orientation="down"
                variant="dark"
                size="sm"
                slot="icon-closed"
            />

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </VsAccordionItem>
        <VsAccordionItem
            :open-by-default="false"
            variant="transparent"
            control-id="accordion_item_3"
        >
            <span slot="title">
                <VsIcon
                    name="cycle"
                    variant="dark"
                    size="sm"
                    class="mr-2"
                />
                Cycling
            </span>

            <VsIcon
                name="chevron"
                variant="dark"
                size="sm"
                slot="icon-open"
            />

            <VsIcon
                name="chevron"
                orientation="down"
                variant="dark"
                size="sm"
                slot="icon-closed"
            />

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </VsAccordionItem>
    </VsAccordion>
  ```

### Responsive
  You can pass in a breakpoint prop that will display the content unhidden
  which will turn into a collapsed accordion when the breakpoint
  specified is reached. This is useful for condensing content on smaller screens
  without having to hide it for larger viewports.

  ```js
    <VsAccordion break-point="md">
        <VsAccordionItem
            variant="transparent"
            control-id="accordion_item_4"
        >
            <span slot="title">
                <VsIcon
                    name="walk"
                    variant="dark"
                    size="sm"
                    class="mr-2"
                />
                Walking
            </span>

            <span slot="icon-open">
                <VsIcon
                    name="chevron"
                    orientation="down"
                    variant="dark"
                    size="xs"
                />
            </span>

            <span slot="icon-closed">
                <VsIcon
                    name="chevron"
                    variant="dark"
                    size="xs"
                />
            </span>

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </VsAccordionItem>

        <VsAccordionItem
            :open-by-default="false"
            variant="transparent"
            control-id="accordion_item_5"
        >
            <span slot="title">
                <VsIcon
                    name="car"
                    variant="dark"
                    size="sm"
                    class="mr-2"
                />
                Driving
            </span>

            <span slot="icon-open">
                <VsIcon
                    name="chevron"
                    orientation="down"
                    variant="dark"
                    size="xs"
                />
            </span>

            <span slot="icon-closed">
                <VsIcon
                    name="chevron"
                    variant="dark"
                    size="xs"
                />
            </span>

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </VsAccordionItem>
        <VsAccordionItem
            :open-by-default="false"
            variant="transparent"
            control-id="accordion_item_6"
        >
            <span slot="title">
                <VsIcon
                    name="cycle"
                    variant="dark"
                    size="sm"
                    class="mr-2"
                />
                Cycling
            </span>

            <span slot="icon-open">
                <VsIcon
                    name="chevron"
                    orientation="down"
                    variant="dark"
                    size="xs"
                />
            </span>

            <span slot="icon-closed">
                <VsIcon
                    name="chevron"
                    variant="dark"
                    size="xs"
                />
            </span>

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </VsAccordionItem>
    </VsAccordion>

  ```

## Accessibility
Each *AccordionItem* must have a unique `controlId` which will
be used for the ARIA attribute `aria-controls` to identify the
content panel that is controlled by the relevant button.

If a user has JavaScript turned off, each *AccordionItem* will be open
by default with the toggle buttons removed so the content is always available.

</docs>
