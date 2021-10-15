<template>
  <div>
    <div id="search">
      <a-input-search
        v-model="queryString"
        placeholder="I'm looking for:"
        @search="fetchTopics()"
      />
    </div>
    <a-list
      id="response-list"
      v-if="queryResponse"
      item-layout="horizontal"
      :data-source="queryResponse"
      :loading="loading"
    >
      <a-list-item slot="renderItem" slot-scope="item">
        <a-list-item-meta>
          <a slot="title" :href="item.topicUrl" target="_blank">{{
            item.topic
          }}</a>
        </a-list-item-meta>
      </a-list-item>
    </a-list>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      queryString: "",
      queryResponse: [],
      loading: false,
    };
  },
  methods: {
    async fetchTopics() {
      if (this.queryString) {
        this.loading = true;
        const response = await this.$axios.$get(
          "http://localhost:8080/scrape?scrapeQuery=" + this.queryString
        );
        this.queryResponse = response;
        console.log(this.queryResponse);
        this.loading = false;
      }
    },
  },
};
</script>

<style lang="css">
#search {
  max-width: 35%;
  margin: 2%;
}
#search-button {
  margin-top: 1%;
}
#response-list {
  margin: 1%;
  max-width: 70%;
}
</style>