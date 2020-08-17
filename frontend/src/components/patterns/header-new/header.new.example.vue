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
                    
                    
                    <VsMegaNavTopMenuItem
                        v-for="(item, index) in header.mainNav"
                        :key="index"
                    >
                        {{item.title}}
                        
                        <template slot="subnav">
                            Test {{item.title}}
                        </template>
                    </VsMegaNavTopMenuItem>
                </template>
            </VsMegaNav>
        </template>
    </VsHeaderNew>
</template>
