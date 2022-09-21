## Usage
Use the warning component to display a warning message aimed at the end user. It contains a message
with an optional second line and optional button.

Various options can be defined controlling the size, alignment, theme and icon. Note that the secondary
theme has a dark background with 80% opacity, designed to sit on top images or similar content.

Automatically expands to fill whatever container it is placed in - usually
covering or replacing an existing element

  ```js
    <div
        class="position-relative mb-4"
        style="width: 30em; height: 20em; background: black;"
    >
        <VsWarning>
            JavaScript is needed to watch this video.
        </VsWarning>
    </div>

    <div
        class="position-relative"
        style="width: 30em; height: 24em;"
    >
        <VsWarning
            theme="light"
            align="right"
        >
            JavaScript is needed to watch this video.

            <template slot="extra-content">
                You can turn on Javascript in your browser settings.
            </template>
        </VsWarning>
    </div>
  ```

### Cookie preference centre link
A link to OneTrust's preference centre

  ```js
    <div
        class="position-relative mb-12"
        style="width: 25em;"
    >
        <VsWarning
            type="cookie"
            theme="dark"
            size="small"
        >
            You need cookies enabled to view this content.

            <template slot="button-text">
                Manage cookies settings
            </template>
        </VsWarning>
    </div>

    <div
        class="position-relative mb-12"
        style="width: 40em; height: 30em;"
    >
        <VsWarning
            type="cookie"
            align="right"
            theme="light"
        >
            You need cookies enabled to view this content.

            <template slot="button-text">
                Manage cookies settings
            </template>
        </VsWarning>
    </div>

  ```
