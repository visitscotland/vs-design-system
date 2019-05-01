<template>
  <div class="template-example">
    <vs-hero
      image-src="https://cimg.visitscotland.com/cms-images/holidays/new/camping-beach-dunes-dog?size=md"
      type="section"
      display-text="Example template"
      display-text-sub="2 columns"
    />

    <vs-container>
      <vs-row>
        <vs-col cols="9">
          <vs-heading display="4" level="1" sub="Holidays"
            >Pet friendly holidays in Scotland</vs-heading
          >
        </vs-col>
      </vs-row>
      <vs-row>
        <vs-col cols="9">
          <vs-article>
            <vs-content-section article-introduction>
              <p>
                Have you ever been on holiday and not enjoyed it because your furry - and, let's
                face it, quite possibly favourite (!) - family member was left behind at home?
              </p>
              <p>
                <a href="https://www.visitscotland.com/holidays-breaks/pet-friendly/">Test link</a>
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
                stables so that you can take your horse on holiday, Scotland offers a wonderful
                range of pet friendly accommodation options that will make your trip really special.
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
        </vs-col>
        <vs-col cols="3">
          <aside>
            <vs-sidebar>
              <vs-content-section heading="11 pawesome ideas for dog friendly days out">
                <p>
                  <a
                    title="Dog friendly days out"
                    href="/blog/activities/dog-friendly-days-out-in-scotland/"
                  >
                    <img
                      alt="Casper dog at Dunskey Castle.jpg"
                      src="https://cimg.visitscotland.com/cms-images/holidays/casper-dunskey?size=sm"
                    />
                  </a>
                </p>
                <p>
                  Get more&nbsp;inspiration from this paw-fect selection of
                  <a
                    title="Dog friendly days out"
                    href="/blog/activities/dog-friendly-days-out-in-scotland/"
                    >activities for you and your pooch</a
                  >&nbsp;- they've been tried and tested by our four-legged friends over on
                  Instagram!
                </p>
              </vs-content-section>
            </vs-sidebar>
          </aside>
        </vs-col>
      </vs-row>
    </vs-container>
  </div>
</template>

<script>
import VsContainer from "../elements/Container"
import VsRow from "../elements/Row"
import VsCol from "../elements/Col"
import VsHeading from "../elements/Heading"
import VsHero from "../patterns/Hero"
import VsArticle from "../patterns/Article"
import VsSidebar from "../patterns/Sidebar"
import VsContentSection from "../patterns/ContentSection"

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
  components: { VsContainer, VsRow, VsCol, VsHero, VsHeading, VsArticle, VsContentSection },
}
</script>

<style lang="scss" scoped>
.template-example {
  border: 1px dashed #ccc;
}
</style>

<docs>
  ```jsx
  <VsExampleTemplate />
  ```
</docs>
