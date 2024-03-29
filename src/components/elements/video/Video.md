## Usage
Use the video component to embed a YouTube video on a page. Video content is uploaded to 
YouTube and then displayed in our component using the YouTube API. The YouTube player has all the 
standard YouTube controls to allow the user to interact with the video.

### Default
Include a YouTube `video-id` so the component knows which video to load. 

 ```jsx
    <VsVideo 
        video-id="c05sg3G4oA4" 
        error-message="Sorry, something's gone wrong. Please try again later"
        cookie-link-text="Manage cookies"
        no-js-message="You need Javascript enabled to see this video"
        no-cookies-message="You need cookies enabled to see this video"
    />
  ```

### Events
The player can also be controlled by other components by emitting a `video-controls` 
event which can be used to play or pause the video.

  ```jsx
    <VsVideo
        video-id="c05sg3G4oA4"
        class="mb-6"
        error-message="Sorry, something's gone wrong. Please try again later"
        cookie-link-text="Manage cookies"
        no-js-message="You need Javascript enabled to see this video"
        no-cookies-message="You need cookies enabled to see this video"
    />

    <VsButton
        @click.native="$root.$emit('video-controls', 'play', 'c05sg3G4oA4')"
        class="mr-4"
    >
        Play
    </VsButton>

    <VsButton
        @click.native="$root.$emit('video-controls', 'pause', 'c05sg3G4oA4')"
    >
        Pause
    </VsButton>
  ```
### Time
The video component stores the time in a user friendly format that can be used in other components. Pass in 
text for single and plural descriptions which should contain '%s' to be replaced by the number of minutes by the component.

When using with a <a href="/#/Patterns/Video%20Caption">VideoCaption</a> with matching `videoId`, 
the time is automatically added using these video prop values. 

   ```jsx
    <VsVideo
        video-id="dKI8IEnqvbU"
        single-minute-descriptor="%s minute video"
        plural-minute-descriptor="%s minutes video"
        language="nl-nl"
        error-message="Sorry, something's gone wrong. Please try again later"
        cookie-link-text="Manage cookies"
        no-js-message="You need Javascript enabled to see this video"
        no-cookies-message="You need cookies enabled to see this video"
    />
  ```

  ### No cookies and no JavaScript messaging
  The video component uses the YouTube API which requires JavaScript and will set cookies. Use these prop values to define messaging that displays if the user doesn't have JavaScript enabled, or hasn't allowed the required cookies.

  ```jsx
    <VsVideo
        video-id="dKI8IEnqvbU"
        single-minute-descriptor="%s minute video"
        plural-minute-descriptor="%s minutes video"
        error-message="Sorry, something's gone wrong. Please try again later"
        cookie-link-text="Manage cookies"
        no-js-message="You need Javascript enabled to see this video"
        no-cookies-message="You need cookies enabled to see this video"
    />
  ```

  ## Accessibility
- Ensure videos never begin playing on page load without the user's consent. This can confuse and distract users from using your product.

- Include the video length in a user-friendly format where possible to allow the user to understand how much time they will need to spend watching the video.

- The video preview image should be clear and not obstruct the play button.

- The user should know what the video is about before being asked to watch so ensure titles are descriptive and concise. 