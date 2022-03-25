## Usage
Use the video component to embed a YouTube video on a page. Video content is uploaded to 
YouTube and then displayed in our component using the YouTube API. The YouTube player has all the 
main YouTube controls to allow the user to interact with the video.

### Default
Include a YouTube `video-id` so the component knows which video to load. 

 ```jsx
    <VsVideo 
        video-id="c05sg3G4oA4" 
    />
  ```

### Events
The player can also be controlled by other components by emitting or listening for a `videoControls` 
event which can be used to play or pause the video.

  ```jsx
    <VsVideo
        video-id="c05sg3G4oA4"
        language="es-es"
        class="mb-6"
    />

    <VsButton
        @click.native="$root.$emit('videoControls', 'play', 'c05sg3G4oA4')"
        class="mr-4"
    >
        Play
    </VsButton>

    <VsButton
        @click.native="$root.$emit('videoControls', 'pause', 'c05sg3G4oA4')"
    >
        Pause
    </VsButton>
  ```
  