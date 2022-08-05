## Usage
The button element allows the user to interact with the page and should 
be used to trigger actions. Do not use a button for navigational elements, 
use the <a href="#/Elements/Link">link</a> component to take users to a new page.

### Primary
The primary button has the most emphasis and should be used for the main call 
to action on the page. Only one should be used per page or section to avoid confusing users. 
This is the default button style so the variant prop does not need to 
be passed for this to display.

```jsx
    <VsButton>
        Nearby places to eat
    </VsButton>
```

### Secondary
The secondary button has less emphasis and should be used alongside a 
primary button for secondary actions on the page. 
```jsx
    <VsButton variant="secondary">
        Nearby places to eat
    </VsButton>

    <VsButton variant="secondary" icon="food">
        Nearby places to eat
    </VsButton>
```

### Dark Theme
When displaying a button on a dark themed background, the colour variant 
should change so the button is more accessible. Use the `darkTheme` prop to update.
```jsx
    <BsWrapper class="bg-dark px-4 pb-2 pt-4">
        <VsButton variant="primary-on-dark" class="mr-2 mb-2">
            Nearby places to eat
        </VsButton>
        <VsButton variant="secondary-on-dark" class="mr-2 mb-2">
            Nearby places to eat
        </VsButton>

        <VsButton variant="primary-on-dark" icon="food" class="mr-2 mb-2">
            Nearby places to eat
        </VsButton>
        <VsButton variant="secondary-on-dark" icon="food" class="mr-2 mb-2">
            Nearby places to eat
        </VsButton>
    </BsWrapper>
```

### Other Variants
Other variants available include `Dark` and `Light`. 
These can be used to help improve contrast between different backgrounds and
for lower priority call to actions. 
```jsx
    <BsWrapper class="d-flex flex-wrap p-4 mb-6" style="border: 1px solid #E0E0E0;">
        <VsButton variant="light" icon="food">
            Nearby places to eat
        </VsButton>
    </BsWrapper>

    <BsWrapper class="p-4" style="background: #fcca1b;">
        <VsButton variant="dark" icon="food">
            Nearby places to eat
        </VsButton>
    </BsWrapper>
```

### Button Size
Buttons are medium size by default but you can use small, large or block buttons depending 
on copy size and space in your component.
```jsx
    <VsButton size="sm" class="mr-2 mb-2" >
        Nearby places to eat
    </VsButton>
    <VsButton size="md" class="mr-2 mb-2">
        Nearby places to eat
    </VsButton>
    <VsButton size="lg" class="mr-2 mb-3">
        Nearby places to eat
    </VsButton>
    <VsButton block class="mr-2 mb-2">
        Nearby places to eat
    </VsButton>
```

### Button with Icon
You can use any icon on a button to help the user understand the meaning and what it does. 
```jsx
    <VsButton icon="food">
        Nearby places to eat
    </VsButton>
```
Icons can be right or left aligned and the icon orientation can also be changed.
```jsx
    <BsWrapper class="d-flex flex-wrap">
        <VsButton icon="chevron" icon-position="left" icon-orientation="left" class="mr-2 mb-2">
            Previous
        </VsButton>
        <VsButton icon="chevron" icon-position="right" icon-orientation="right" class="mr-2 mb-2">
            Next
        </VsButton>
    </BsWrapper>
```

Icons can be used with any button variant and the size and colour of the icon are automatically 
calculated to match. These can be overridden if needed for an edge case (see Props chart below).
```jsx
    <VsButton icon="food" size="sm" variant="light" class="mr-2 mb-4">
        Nearby places to eat
    </VsButton>
    <VsButton icon="food" size="lg" variant="secondary" class="mr-2 mb-2">
        Nearby places to eat
    </VsButton>
    
```

### Icon Only 
Icon buttons without text should only be used when the context is totally clear to the user as 
to what the button does. When using an icon only button, you must provide screen 
reader only text for accessibility.
```jsx
    <VsButton icon-only icon="search" size="md" class="mr-6">
        <span class="sr-only">
            Search
        </span>
    </VsButton>

    <VsButton icon-only icon="close" size="lg" variant="transparent">
        <span class="sr-only">
            Close
        </span>
    </VsButton>
```

### Disabled States
Disabled buttons tell the user that something is wrong. These buttons should only be used 
if it is clear to the user why the button is disabled. 

```jsx
    <BsWrapper class="d-flex flex-wrap">
        <VsButton disabled icon="food">
            Nearby places to eat
        </VsButton>
    </BsWrapper>
```

## Accessibility
- Ensure `space` and `enter` keyboard keys activate the button.

- Ensure the button has an accessible label that describes the action of the button.

- Use the correct ARIA labels for your intended purpose, see <a href="https://www.w3.org/TR/wai-aria-practices-1.1/#button">W3C WAI-ARIA Authoring Practices - Button</a>.
