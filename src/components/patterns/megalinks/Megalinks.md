## Usage
TBC

### Megalinks Multi Image
```jsx
    <VsMegalinks
        title="A megalinks multi image component"
        class="vs-megalinks--multi-image"
        buttonLink="http://www.visitscotland.com"
        noJsMessage="JavaScript is needed to watch this video."
        noCookiesMessage="Cookies are needed to watch this video."
        :noCookiesLink="{
            url: 'https://google.com',
            label: 'Update my cookie settings'
        }"
    >
        <template slot="vsMegalinksIntro">
            <p>Sed at mauris a est dictum luctus. Nullam viverra
            pellentesque dolor, id elementum neque viverra quis.
            Morbi lacinia est id risus facilisis porttitor ut ac mi.
            Maecenas bibendum sodales nisi eu luctus.</p>
        </template>
        <VsCol
            cols="10"
            class="offset-1"
        >
            <VsContainer>
                <VsRow>
                    <VsCol
                        cols="12"
                        md="6"
                        xl="12"
                    >
                        <VsMegalinkMultiImage
                            featured
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            imgAlt="This is the alt text"
                            linkType="video"
                            linkUrl="#"
                            videoId="g-Fhvj7vW-E"
                            videoBtnText="Play Video"
                            errorMessage="We're sorry, there's been an error"
                        >
                            <template slot="vsMultiImageHeading">
                                The Edinburgh International Festival
                            </template>
                            <template slot="vsMultiImageContent">
                                <p>Right across the country, you’ll find amazing
                                places to eat and drink
                                from local markets to renowned restaurants.</p>
                            </template>
                        </VsMegalinkMultiImage>
                        <VsModal
                            modalId="g-Fhvj7vW-E"
                            closeBtnText="Close"
                            :isVideoModal="true"
                        >
                            <VsRow>
                                <VsCol cols="12">
                                    <VsVideo
                                        videoId="g-Fhvj7vW-E"
                                        class="mb-8"
                                        no-js-message="You need Javascript enabled"
                                        no-cookies-message="You need cookies enabled"
                                        cookie-btn-text="Cookie button"
                                    />
                                </VsCol>
                            </VsRow>
                        </VsModal>
                    </VsCol>
                    <VsCol
                        cols="12"
                        md="6"
                        xl="4"
                    >
                        <VsMegalinkMultiImage
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            imgAlt="This is the alt text 1"
                            linkType="external"
                            linkUrl="https://www.visitscotland.com"
                            errorMessage="Something went wrong"
                        >
                            <template slot="vsMultiImageHeading">
                                Count 7,000 shining stars in the iconic galloway forest
                            </template>
                            <template slot="vsMultiImageContent">
                                <p>Right across the country, you’ll find amazing
                                places to eat and drink from local markets to renowned
                                restaurants. Here are some recomm…</p>
                            </template>
                        </VsMegalinkMultiImage>
                    </VsCol>
                    <VsCol
                        cols="12"
                        md="6"
                        xl="4"
                    >
                        <VsMegalinkMultiImage
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            imgAlt="This is the alt text 2"
                            linkType="video"
                            linkUrl="#"
                            videoId="N3r5rCN9iaE"
                            videoBtnText="Play Video"
                            errorMessage="We're sorry, there's been an error"
                        >
                            <template slot="vsMultiImageHeading">
                                Count 7,000 shining stars in the iconic galloway forest
                            </template>
                            <template slot="vsMultiImageContent">
                                <p>Right across the country, you’ll find amazing
                                places to eat and drink
                                from local markets to renowned restaurants.
                                Here are some recomm…</p>
                                <p>Right across the country, you’ll find amazing place
                                to eat and drink
                                from local markets to renowned restaurants. Here are
                                some recomm…</p>
                                <p>Right across the country, you’ll find amazing places
                                to eat and drink
                                from local markets to renowned restaurants. Here are
                                some recomm…</p>
                            </template>
                        </VsMegalinkMultiImage>
                        <VsModal
                            modalId="N3r5rCN9iaE"
                            closeBtnText="Close"
                            :isVideoModal="true"
                        >
                            <VsRow>
                                <VsCol cols="12">
                                    <VsVideo
                                        videoId="N3r5rCN9iaE"
                                        class="mb-8"
                                        no-js-message="You need Javascript enabled"
                                        no-cookies-message="You need cookies enabled"
                                        cookie-btn-text="Cookie button"
                                    />
                                </VsCol>
                            </VsRow>
                        </VsModal>
                    </VsCol>
                    <VsCol
                        cols="12"
                        md="6"
                        xl="4"
                    >
                        <VsMegalinkMultiImage
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            linkType="download"
                            linkUrl="https://www.visitscotland.com"
                            errorMessage="Something went wrong"
                        >
                            <template slot="vsMultiImageHeading">
                                Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                            </template>
                            <template slot="vsMultiImageContent">
                                <p>Right across the country, you’ll find amazing
                                places to eat and drink from local markets to renowned
                                restaurants. Here are some recomm…</p>
                            </template>
                        </VsMegalinkMultiImage>
                    </VsCol>
                    <VsCol
                        cols="12"
                        md="6"
                    >
                        <VsMegalinkMultiImage
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            linkType="download"
                            linkUrl="https://www.visitscotland.com"
                            errorMessage="Something went wrong"
                        >
                            <template slot="vsMultiImageHeading">
                                Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                            </template>
                            <template slot="vsMultiImageContent">
                                <p>Right across the country, you’ll find amazing
                                places to eat and drink from local markets to renowned
                                restaurants. Here are some recomm…</p>
                            </template>
                        </VsMegalinkMultiImage>
                    </VsCol>
                    <VsCol
                        cols="12"
                        md="6"
                    >
                        <VsMegalinkMultiImage
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            linkType="download"
                            linkUrl="https://www.visitscotland.com"
                            errorMessage="Something went wrong"
                        >
                            <template slot="vsMultiImageHeading">
                                Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                            </template>
                            <template slot="vsMultiImageContent">
                                <p>Right across the country, you’ll find amazing
                                places to eat and drink from local markets to renowned
                                restaurants. Here are some recomm…</p>
                            </template>
                        </VsMegalinkMultiImage>
                    </VsCol>
                    <VsCol
                        cols="12"
                        md="6"
                        xl="12"
                    >
                        <VsMegalinkMultiImage
                            featured
                            lastFeatured
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            imgAlt="This is the alt text"
                            linkType="internal"
                            linkUrl="https://www.visitscotland.com"
                            days="6"
                            daysLabel="days"
                            transport="bus"
                            transportName="bus"
                            errorMessage="Something went wrong"
                        >
                            <template slot="vsMultiImageHeading">
                                The Edinburgh International Festival
                            </template>
                            <template slot="vsMultiImageContent">
                                <p>Right across the country, you’ll find amazing
                                places to eat and drink
                                from local markets to renowned restaurants.</p>
                            </template>
                        </VsMegalinkMultiImage>
                    </VsCol>
                </VsRow>
            </VsContainer>
        </VsCol>
        <template slot="vsMegalinksButton">
            Button Text
        </template>
    </VsMegalinks>
  ```

### Megalinks Single Image

```jsx
    <VsMegalinks
        title="A megalinks single image component"
        class="vs-megalinks--single-image"
        buttonLink="http://www.visitscotland.com"
        variant="single-image"
    >
        <VsCol cols="12">
            <vs-megalink-single-image
                title="The Component heading"
                buttonLink="www.visitscotland.com"
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            >
                <template slot="vsSingleImage">
                    <VsImageWithCaption
                        mobile-overlap
                        alt-text="Image alt text"
                        image-src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        errorMessage="Something went wrong"
                    >
                        <VsCaption
                            slot="img-caption"
                            text-align="right"
                        >
                            <template slot="caption">
                                An image of Scotland
                            </template>

                            <template slot="credit">
                                @2020 Credit here
                            </template>
                        </VsCaption>
                    </VsImageWithCaption>
                </template>
                <template slot="vsSingleImageContent">
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        Integer et eros at est dignissim interdum. Fusce nisl metus,
                        pharetra eu feugiat vitae, porttitor eget est.
                    </p>
                </template>
                <template slot="vsSingleImageLinks">
                    <li class="vs-megalink-single-image__link-list-item">
                        <VsLink
                            href="www.visitscotland.com"
                        >
                            This is a link here
                        </VsLink>
                    </li>
                    <li class="vs-megalink-single-image__link-list-item">
                        <VsLink
                            href="www.visitscotland.com"
                            type="external"
                        >
                            This is an external link here
                        </VsLink>
                    </li>
                    <li class="vs-megalink-single-image__link-list-item">
                        <VsLink
                            href="www.visitscotland.com"
                            type="download"
                        >
                            This is a download link here
                        </VsLink>
                    </li>
                </template>
                <template slot="vsSingleImageButtonText">
                    This is the button
                </template>
            </vs-megalink-single-image>
        </VsCol>
    </VsMegalinks>
    <VsMegalinks
        title="A second, dark version"
        class="vs-megalinks--single-image"
        buttonLink="http://www.visitscotland.com"
        variant="single-image"
        theme="dark"
    >
        <template slot="vsMegalinksIntro">
            <p>Sed at mauris a est dictum luctus. Nullam viverra
            pellentesque dolor, id elementum neque viverra quis.
            Morbi lacinia est id risus facilisis porttitor ut ac mi.
            Maecenas bibendum sodales nisi eu luctus.</p>
        </template>
        <VsCol cols="12">
            <VsMegalinkSingleImage
                title="This is the second component heading"
                theme="dark"
                buttonLink="www.visitscotland.com"
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                alternate
            >
                <template slot="vsSingleImage">
                    <VsImageWithCaption
                        mobile-overlap
                        alt-text="Image alt text"
                        image-src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        errorMessage="Something went wrong"
                    >
                        <VsCaption
                            slot="img-caption"
                            text-align="left"
                        >
                            <template slot="caption">
                                An image of Scotland
                            </template>

                            <template slot="credit">
                                @2020 Credit here
                            </template>
                        </VsCaption>
                    </VsImageWithCaption>
                </template>
                <template slot="vsSingleImageContent">
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        Integer et eros at est dignissim interdum. Fusce nisl metus,
                        pharetra eu feugiat vitae, porttitor eget est.
                    </p>
                </template>
                <template slot="vsSingleImageLinks">
                    <VsLinkListItem
                        href="www.visitscotland.com"
                        variant="on-dark"
                    >
                        This is a link here
                    </VsLinkListItem>
                    <VsLinkListItem
                        href="www.visitscotland.com"
                        type="external"
                        variant="on-dark"
                    >
                        This is an external link here
                    </VsLinkListItem>
                    <VsLinkListItem
                        href="www.visitscotland.com"
                        type="download"
                        variant="on-dark"
                    >
                        This is a download link here
                    </VsLinkListItem>
                </template>
                <template slot="vsSingleImageButtonText">
                    This is the button
                </template>
            </VsMegalinkSingleImage>
        </VsCol>
    </VsMegalinks>

  ```


### Megalinks List View
```jsx
<VsMegalinks
        title="A megalinks link list component"
        class="vs-megalinks--link-list"
        buttonLink="http://www.visitscotland.com"
        variant="link-list"
    >
        <VsCol
            cols="12"
            md="6"
        >
            <vs-megalink-link-list
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                imgAlt="This is the alt text"
                linkType="video"
                linkUrl="#"
                videoId="tfk7J6XZju4"
                videoBtnText="Play Video"
                errorMessage="We're sorry, there's been an error"
            >
                <template slot="vsLinkListHeading">
                    The Edinburgh International Festival and summer festival
                </template>
                <template slot="vsLinkListContent">
                    <p>Right across the country, you’ll find amazing places
                    to eat and drink from local markets to renowned
                    restaurants.</p>
                </template>
            </vs-megalink-link-list>
            <VsModal
                modalId="tfk7J6XZju4"
                closeBtnText="Close"
                :isVideoModal="true"
            >
                <VsRow>
                    <VsCol cols="12">
                        <VsVideo
                            videoId="tfk7J6XZju4"
                            class="mb-8"
                            no-js-message="You need Javascript enabled"
                            no-cookies-message="You need cookies enabled"
                            cookie-btn-text="Cookie button"
                        />
                    </VsCol>
                </VsRow>
            </VsModal>
        </VsCol>
        <VsCol
            cols="12"
            md="6"
        >
            <vs-megalink-link-list
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                imgAlt="This is the alt text 1"
                linkType="external"
                linkUrl="https://www.visitscotland.com"
                errorMessage="Something went wrong"
            >
                <template slot="vsLinkListHeading">
                    Count 7,000 shining stars in the iconic galloway forest
                </template>
                <template slot="vsLinkListContent">
                    <p>Right across the country, you’ll find amazing
                    places to eat and drink from local markets to renowned
                    restaurants. Here are some recomm…</p>
                </template>
            </vs-megalink-link-list>
        </VsCol>
        <VsCol
            cols="12"
            md="6"
        >
            <vs-megalink-link-list
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                imgAlt="This is the alt text 2"
                linkType="video"
                linkUrl="#"
                videoId="zZCUFjSiWpE"
                videoBtnText="Play Video"
                errorMessage="We're sorry, there's been an error"
            >
                <template slot="vsLinkListHeading">
                    Count 7,000 shining stars in the iconic galloway forest
                </template>
                <template slot="vsLinkListContent">
                    <p>Right across the country, you’ll find amazing places
                    to eat and drink
                    from local markets to renowned restaurants.
                    Here are some recomm…</p>
                </template>
            </vs-megalink-link-list>
            <VsModal
                modalId="zZCUFjSiWpE"
                closeBtnText="Close"
                :isVideoModal="true"
            >
                <VsRow>
                    <VsCol cols="12">
                        <VsVideo
                            videoId="zZCUFjSiWpE"
                            class="mb-8"
                            no-js-message="You need Javascript enabled"
                            no-cookies-message="You need cookies enabled"
                            cookie-btn-text="Cookie button"
                        />
                    </VsCol>
                </VsRow>
            </VsModal>
        </VsCol>
        <VsCol
            cols="12"
            md="6"
        >
            <vs-megalink-link-list
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                linkType="download"
                linkUrl="https://www.visitscotland.com"
                errorMessage="Something went wrong"
            >
                <template slot="vsLinkListHeading">
                    Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                </template>
                <template slot="vsLinkListContent">
                    <p>Right across the country, you’ll find amazing
                    places to eat and drink from local markets to renowned
                    restaurants. Here are some recomm…</p>
                </template>
            </vs-megalink-link-list>
        </VsCol>
        <template slot="vsMegalinksButton">
            Button Text
        </template>
    </VsMegalinks>

    <VsMegalinks
        theme="dark"
        title="A megalinks link list component"
        class="vs-megalinks--link-list"
        buttonLink="http://www.visitscotland.com"
        variant="link-list"
    >
        <VsCol
            cols="12"
            md="6"
        >
            <VsMegalinkLinkList
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                imgAlt="This is the alt text"
                linkType="internal"
                theme="dark"
                linkUrl="https://www.visitscotland.com"
                errorMessage="Something went wrong"
            >
                <template slot="vsLinkListHeading">
                    The Edinburgh International Festival and summer festival
                </template>
                <template slot="vsLinkListContent">
                    <p>Right across the country, you’ll find amazing places
                    to eat and drink from local markets to renowned
                    restaurants.</p>
                </template>
            </VsMegalinkLinkList>
        </VsCol>
        <VsCol
            cols="12"
            md="6"
        >
            <VsMegalinkLinkList
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                imgAlt="This is the alt text 1"
                linkType="external"
                theme="dark"
                linkUrl="https://www.visitscotland.com"
                errorMessage="Something went wrong"
            >
                <template slot="vsLinkListHeading">
                    Count 7,000 shining stars in the iconic galloway forest
                </template>
                <template slot="vsLinkListContent">
                    <p>Right across the country, you’ll find amazing
                    places to eat and drink from local markets to renowned
                    restaurants. Here are some recomm…</p>
                </template>
            </VsMegalinkLinkList>
        </VsCol>
        <VsCol
            cols="12"
            md="6"
        >
            <VsMegalinkLinkList
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                imgAlt="This is the alt text 2"
                linkType="external"
                theme="dark"
                linkUrl="https://www.visitscotland.com"
                errorMessage="Something went wrong"
            >
                <template slot="vsLinkListHeading">
                    Count 7,000 shining stars in the iconic galloway forest
                </template>
                <template slot="vsLinkListContent">
                    <p>Right across the country, you’ll find amazing places
                    to eat and drink
                    from local markets to renowned restaurants.
                    Here are some recomm…</p>
                </template>
            </VsMegalinkLinkList>
        </VsCol>
        <VsCol
            cols="12"
            md="6"
        >
            <VsMegalinkLinkList
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                linkType="download"
                theme="dark"
                linkUrl="https://www.visitscotland.com"
                errorMessage="Something went wrong"
            >
                <template slot="vsLinkListHeading">
                    Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                </template>
                <template slot="vsLinkListContent">
                    <p>Right across the country, you’ll find amazing
                    places to eat and drink from local markets to renowned
                    restaurants. Here are some recomm…</p>
                </template>
            </VsMegalinkLinkList>
        </VsCol>

        <template slot="vsMegalinksButton">
            Button Text
        </template>
    </VsMegalinks>
```