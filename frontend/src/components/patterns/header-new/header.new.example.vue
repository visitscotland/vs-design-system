<!-- eslint-disable -->
<template>
    <VsHeaderNew>
        <template slot="global-navbar">
            <div
                class="navbar bg-primary-purple"
                style="height: 35px"
            >
                <VsContainer>
                    <VsRow>
                        <VsCol cols="12">
                            <span style="color: white">Global Nav</span>
                        </VsCol>
                    </VsRow>
                </VsContainer>
            </div>
        </template>
        <template slot="mega-nav">
            <VsMegaNav>
                <template slot="mega-nav-top-menu-items">
                    <VsMegaNavTopMenuItem>
                        Testing 1
                       
                    </VsMegaNavTopMenuItem>

                    <VsMegaNavTopMenuItem>
                        Testing 2
                    </VsMegaNavTopMenuItem>
                    <VsMegaNavTopMenuItem>
                        Testing 3
                    </VsMegaNavTopMenuItem>
                </template>
            </VsMegaNav>
        </template>
    </VsHeaderNew>
</template>
