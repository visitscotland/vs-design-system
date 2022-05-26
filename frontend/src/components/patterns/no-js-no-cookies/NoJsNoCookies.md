## Usage
Use the No JS / No Cookies component to conditionally render vs-warnings if the
user has javascript disabled or if they are missing mandatory cookies for a
given component. This should be present on components where both elements are
needed like 3rd party js embeds and video.

A boolean indicating if there are missing cookies should be supplied by the parent
component, js being disabled is automatically checked within the component.

Javascript errors are displayed with priority over cookie ones.

  ```js
    <div
        class="position-relative no-js"
        style="width: 12em; height: 12em;"
    >
        <VsNoJsNoCookies
            noJsMessage="JavaScript is needed to watch this video."
        />
    </div>
    <br />
    <div
        class="position-relative"
        style="width: 25em; height: 10em;"
    >
        <VsNoJsNoCookies
            noJsMessage="JavaScript is needed to watch this video."
            cookiesMissing="true"
            noCookiesMessage="Cookies are needed to watch this video."
            :noCookiesLink="{
                url: 'https://google.com',
                label: 'Update my cookie settings'
            }"
        />
    </div>
    <br />
    <div
        class="position-relative no-js"
        style="width: 15em; height: 18em;"
    >
        <VsNoJsNoCookies
            cookiesMissing="true"
            noJsMessage="JavaScript is needed to watch this video."
            noCookiesMessage="Cookies are needed to watch this video."
            :noCookiesLink="{
                url: 'https://google.com',
                label: 'Update my cookie settings'
            }"
        />
    </div>

  ```
