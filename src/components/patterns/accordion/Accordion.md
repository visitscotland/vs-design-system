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
                Key Destinations
            </span>

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </VsAccordionItem>

        <VsAccordionItem
            variant="transparent"
            control-id="accordion_item_2"
        >
            <span slot="title">
                Getting There
            </span>

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </VsAccordionItem>
        <VsAccordionItem
            variant="transparent"
            control-id="accordion_item_3"
        >
            <span slot="title">
                Public Transport
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

### Responsive
  You can pass in a breakpoint prop that will display the content and then turn into
  a collapsed accordion when the breakpoint
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
                    class="mr-1"
                />
                Walking
            </span>

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </VsAccordionItem>

        <VsAccordionItem
            variant="transparent"
            control-id="accordion_item_5"
        >
            <span slot="title">
                <VsIcon
                    name="car"
                    variant="dark"
                    size="sm"
                    class="mr-1"
                />
                Driving
            </span>

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </VsAccordionItem>
        <VsAccordionItem
            variant="transparent"
            control-id="accordion_item_6"
        >
            <span slot="title">
                <VsIcon
                    name="cycle"
                    variant="dark"
                    size="sm"
                    class="mr-1"
                />
                Cycling
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

### Toggle Icon
The default icon used in the toggle button can be changed if needed 
using the slots provided. The arrow must be down
when the accordion item is closed, and up when accordion item is open. 

```js
<VsAccordion>
    <VsAccordionItem
        variant="transparent"
        control-id="accordion_item_7"
    >
        <span slot="title">
            Key Destinations
        </span>

        <VsIcon
            name="chevron"
            variant="primary"
            size="sm"
            slot="icon-open"
        />

        <VsIcon
            name="chevron"
            orientation="down"
            variant="primary"
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

## Colour Badge
Each accordion item can be marked with a simple colour badge if needed, to add a visual
indicator that contrasts that item with the others. The colours used should either be part
of the site branding, or clearly attached to the meaning they're indicating.

```js
    <VsAccordion>
        <VsAccordionItem
        variant="transparent"
            control-id="accordion_item_8"
            colour-badge="green"
        >
            <span slot="title">
                Easy
            </span>
            <p>Easy Ski Runs</p>
        </VsAccordionItem>
        <VsAccordionItem
        variant="transparent"
            control-id="accordion_item_9"
            colour-badge="blue"
        >
            <span slot="title">
                Intermediate
            </span>
            <p>Intermediate Ski Runs</p>
        </VsAccordionItem>
        <VsAccordionItem
        variant="transparent"
            control-id="accordion_item_10"
            colour-badge="red"
        >
            <span slot="title">
                Difficult
            </span>
            <p>Difficult Ski Runs</p>
        </VsAccordionItem>
        <VsAccordionItem
        variant="transparent"
            control-id="accordion_item_11"
            colour-badge="black"
        >
            <span slot="title">
                Very Difficult
            </span>
            <p>Very difficult Ski Runs</p>
        </VsAccordionItem>
    </VsAccordion>
```

## Accessibility
Each *AccordionItem* must have a unique `controlId` which will
be used for the ARIA attribute `aria-controls` to identify the
content panel that is controlled by the relevant button.

If a user has JavaScript turned off, each *AccordionItem* will be open
by default with the toggle buttons removed so the content is always available.
