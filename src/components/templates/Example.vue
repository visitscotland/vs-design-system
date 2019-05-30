<template>
  <div>
    <vs-hero
      image-src="https://cimg.visitscotland.com/cms-images/holidays/new/camping-beach-dunes-dog?size=md"
      image-align-v="40%"
      type="section"
      display-text="Responsive template"
      display-text-sub="2 columns"
      display-align-v="top"
    />

    <vs-container>
      <vs-row>
        <vs-col lg="8" xl="9">
          <vs-heading display="4" level="1" sub="Holidays">
            Pet friendly holidays in Scotland
          </vs-heading>
        </vs-col>
      </vs-row>
      <vs-row>
        <vs-article md="7" lg="8" xl="9">
          <vs-content-section lead>
            <p>
              Have you ever been on holiday and not enjoyed it because your furry - and, let's face
              it, quite possibly favourite (!) - family member was left behind at home?
            </p>
            <p>
              Here is a
              <a href="https://www.visitscotland.com/holidays-breaks/pet-friendly/">test link</a> in
              the lead section.
            </p>
          </vs-content-section>

          <vs-content-section>
            <p>
              Scotland's got everything you'll need for a relaxing pet friendly holiday, with dog
              friendly attractions and scenic walking routes, as well as lots of pubs and cafés
              where your pooch will feel right at home!
            </p>
            <p>
              Whether you're looking for a cat or dog friendly hotel or somewhere with on-site
              stables so that you can take your horse on holiday, Scotland offers a wonderful range
              of pet friendly accommodation options that will make your trip really special.
            </p>
            <p>
              Check out our
              <a
                title="10 dog-friendly accommodations that will get your tail wagging"
                href="http://ebooks.visitscotland.com/accommodation-guide/12/"
                >10 dog friendly accommodations that will get your tail wagging!</a
              >
            </p>
          </vs-content-section>
        </vs-article>

        <vs-sidebar md="5" lg="4" xl="3">
          <vs-content-section tag="aside" heading="11 pawesome ideas for dog friendly days out">
            <p>
              <!-- <b-link
                title="Dog friendly days out"
                href="/blog/activities/dog-friendly-days-out-in-scotland/"
              > -->
              <vs-img
                src="https://cimg.visitscotland.com/cms-images/holidays/casper-dunskey?size=sm"
                alt="Casper dog at Dunskey Castle.jpg"
                fluid
              />
              <!-- </b-link> -->
            </p>
            <p>
              Get more&nbsp;inspiration from this paw-fect selection of
              <a
                title="Dog friendly days out"
                href="/blog/activities/dog-friendly-days-out-in-scotland/"
                >activities for you and your pooch</a
              >&nbsp;- they've been tried and tested by our four-legged friends over on Instagram!
            </p>
          </vs-content-section>
        </vs-sidebar>
      </vs-row>
    </vs-container>
  </div>
</template>

<script>
import VsContainer from "../elements/Container"
import VsRow from "../elements/Row"
import VsCol from "../elements/Col"
import VsHeading from "../elements/Heading"
import VsImg from "../elements/Img"
import VsHero from "../patterns/Hero"
import VsArticle from "../patterns/Article"
import VsSidebar from "../patterns/Sidebar"
import VsContentSection from "../patterns/ContentSection"

import BLink from "bootstrap-vue/es/components/link/link"

/**
 *An example page template.
 */
export default {
  name: "VsExampleTemplate",
  status: "prototype",
  release: "0.0.1",
  metaInfo: {
    title: "Template example",
    htmlAttrs: {
      lang: "en",
    },
  },
  components: {
    VsContainer,
    VsRow,
    VsCol,
    VsImg,
    VsHero,
    VsHeading,
    VsArticle,
    VsContentSection,
    BLink,
  },
}
</script>

<docs>
  ```jsx
  <VsExampleTemplate />
  ```
</docs>
