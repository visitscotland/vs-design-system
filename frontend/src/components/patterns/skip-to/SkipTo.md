## Usage
The skip to component allows users who navigate via keyboard to quickly focus on different areas of the page. 

```jsx
    <p>Tab into the element below to see the Skip To component appear:</p>
    <div class="border" style="overflow: hidden; position: relative;">
        <VsSkipTo>
            <VsLink href="#main">
                <span class="sr-only">Skip to </span>Main menu
            </VsLink>
            <VsLink href="#main">
                <span class="sr-only">Skip to </span>Content
            </VsLink>
            <VsLink href="#main">
                <span class="sr-only">Skip to </span>Search
            </VsLink>
            <VsLink href="#main">
                <span class="sr-only">Skip to </span>Footer
            </VsLink>
            <VsLink href="#main">
                <span class="sr-only">Skip to </span>Cookie policy
            </VsLink>
        </VsSkipTo>
    </div>
  ```

