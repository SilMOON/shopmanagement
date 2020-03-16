<template>
  <el-container id="app">
    <el-header>
      <el-menu :default-active="activeIndex" mode="horizontal" @select="handleSelect">
        <el-row>
          <el-col :span="2.5">
            <el-menu-item index="1">Shop Management System</el-menu-item>
          </el-col>
          <el-col :span="2.5">
            <el-menu-item index="2" disabled>Login</el-menu-item>
          </el-col>
          <el-col :span="5" :offset="15" style="margin-top: 10px;">
            <el-input
              placeholder="Which item are you looking for?"
              v-model="input"
              @keyup.enter.native="search"
              size="small"
              clearable
            >
              <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
            </el-input>
          </el-col>
        </el-row>
      </el-menu>
    </el-header>

    <el-container>
      <el-aside width="250px" style="height: 100%;">
        <el-menu default-active="activeIndex">
          <el-menu-item index="1">
            <i class="el-icon-menu"></i>
            <span slot="title">HomePage</span>
          </el-menu-item>
          <el-menu-item index="2" disabled>
            <i class="el-icon-setting"></i>
            <span slot="title">My Account</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <el-row :gutter="20">
          <ul id="goodsList" style="list-style-type:none;">
            <li v-for="item in items" v-bind:key="item.id">
              <el-col :span="6">
                <el-card :body-style="{ padding: '0px' }" shadow="hover" class="item-card">
                  <div style="margin-left: 50%;transform: translateX(-40%);">
                    <img
                      :src="'http://localhost:8181/'+item.item_image_url"
                      class="image"
                      :title="item.title"
                    />
                  </div>

                  <div style="padding: 14px;">
                    <h4
                      style="text-align: center; margin-top:5px; margin-bottom:5px;"
                    >{{ item.title }}</h4>
                    <div style="text-align: center;">Price: {{ item.price }}</div>
                    <div>{{ item.description }}</div>
                    <div class="bottom">
                      <el-row>
                        <el-col :span="2" :offset="3">
                          <el-button
                            type="danger"
                            icon="el-icon-delete"
                            @click="deleteItem"
                            :id="item.id"
                            circle
                          ></el-button>
                        </el-col>
                        <el-col :span="2" :offset="13">
                          <el-button
                            type="primary"
                            icon="el-icon-edit"
                            @click="dialogVisible=true;formTitle='Edit item';isNew=false;currentItem=JSON.parse(JSON.stringify(item))"
                            circle
                          ></el-button>
                        </el-col>
                      </el-row>
                      <el-divider></el-divider>
                      <el-row style="font-style: italic; font-size:small; color: #606266;">
                        <el-col :span="12">Created at: {{ item.created_at }}</el-col>
                        <el-col :span="11" :offset="1">Last edited: {{ item.updated_at }}</el-col>
                      </el-row>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </li>

            <el-dialog
              id="editForm"
              :title="formTitle"
              :visible.sync="dialogVisible"
              width="40%"
              :before-close="handleClose"
            >
              <el-form :model="currentItem" :rules="rules" ref="currentItem" label-width="100px">
                <el-upload
                  class="image-uploader"
                  action="http://localhost:8181/uploadImage"
                  name="image"
                  :show-file-list="false"
                  :on-success="handleUploadSuccess"
                  :before-upload="beforeUpload"
                >
                  <img
                    v-if="currentItem.item_image_url"
                    :src="'http://localhost:8181/'+currentItem.item_image_url"
                    class="image"
                  />
                  <i v-else class="el-icon-plus image-uploader-icon"></i>
                </el-upload>

                <el-form-item label="Title" prop="title">
                  <el-input maxlength="100" v-model="currentItem.title" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="Price" prop="price">
                  <el-input type="float" v-model="currentItem.price"></el-input>
                </el-form-item>
                <el-form-item label="Description" prop="description">
                  <el-input
                    type="textarea"
                    maxlength="500"
                    v-model="currentItem.description"
                    show-word-limit
                  ></el-input>
                </el-form-item>
                <el-form-item style="margin-left: 50%;transform: translateX(-50%);">
                  <el-button @click="dialogVisible = false">Cancel</el-button>
                  <el-button
                    type="primary"
                    @click="dialogVisible=false;submitEdit(currentItem, isNew)"
                  >Submit</el-button>
                </el-form-item>
              </el-form>
            </el-dialog>

            <div id="newItemButton">
              <el-button
                type="primary"
                style="font-size: 2rem;"
                v-if="showNewButton"
                icon="el-icon-circle-plus"
                circle
                @click="dialogVisible=true;formTitle='Add new item';isNew=true;currentItem=JSON.parse(JSON.stringify(emptyItem))"
              ></el-button>
            </div>
            <div id="newItemButton">
              <el-button
                type="warning"
                style="font-size: 2rem;"
                v-if="!showNewButton"
                icon="el-icon-s-home"
                circle
                @click="getList"
              ></el-button>
            </div>
          </ul>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "goodsList",
  data() {
    return {
      items: [],
      input: "",
      activeIndex: "1",
      dialogVisible: false,
      showNewButton: true,
      emptyItem: {
        title: "",
        description: "",
        price: "",
        item_image_url: "",
        created_at: "",
        updated_at: ""
      },
      currentItem: {
        title: "",
        description: "",
        price: "",
        item_image_url: "",
        created_at: "",
        updated_at: ""
      },
      rules: {
        title: [
          {
            required: true,
            message: "Please enter a title",
            trigger: "blur"
          },
          {
            min: 1,
            max: 100,
            message: "Please keep the length of title less than 100",
            trigger: "blur"
          }
        ],
        description: [
          {
            required: true,
            message: "Please enter a description",
            trigger: "blur"
          },
          {
            max: 500,
            message: "Please keep the length of description less than 500",
            trigger: "blur"
          }
        ],
        price: [
          {
            required: true,
            message: "Please enter a price",
            trigger: "blur"
          }
        ]
      },
      formTitle: "",
      isNew: false
    };
  },
  mounted: function() {
    axios
      .get("http://localhost:8181/itemList")
      .then(res => {
        this.items = res.data;
      })
      .catch(err => {
        console.error(err);
      });
  },
  methods: {
    getList: function() {
      axios
        .get("http://localhost:8181/itemList")
        .then(res => {
          this.items = res.data;
        })
        .catch(err => {
          console.error(err);
        });
      this.showNewButton = true;
      this.input = "";
    },
    beforeUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("Please upload JPG file only.");
      }
      if (!isLt2M) {
        this.$message.error("Please upload image smaller than 2MB.");
      }
      return isJPG && isLt2M;
    },
    handleUploadSuccess(res, file) {
      const isInvalid = res === "File not exists" || res === "Invalid file";
      if (!isInvalid) {
        this.currentItem.item_image_url = "goodsimg/" + res;
        this.imageUrl = this.currentItem.item_image_url;
      }
    },
    submitEdit: function(item, isNewItem) {
      this.$refs["currentItem"].validate(valid => {
        if (valid) {
          const postInfo = new URLSearchParams();
          postInfo.append("title", item.title);
          postInfo.append("description", item.description);
          postInfo.append("price", item.price);
          postInfo.append("item_image_url", item.item_image_url);
          var postType;
          if (isNewItem) {
            postType = "http://localhost:8181/addItem";
          } else {
            postType = "http://localhost:8181/editItem?id=" + item.id;
          }
          axios
            .post(postType, postInfo)
            .then(res => {
              axios
                .get("http://localhost:8181/itemList")
                .then(res => {
                  this.items = res.data;
                })
                .catch(err => {
                  console.error(err);
                });
            })
            .catch(err => {
              console.error(err);
            });
        } else {
          console.log("error submit!!");
          alert("Please submit item as requested!");
          return false;
        }
      });
    },
    search: function() {
      axios
        .get("http://localhost:8181/itemList")
        .then(res => {
          axios
            .get("http://localhost:8181/search?title=" + this.input)
            .then(res => {
              this.items = res.data;
            })
            .catch(err => {
              console.error(err);
            });
        })
        .catch(err => {
          console.error(err);
        });
      this.showNewButton = false;
    },
    deleteItem: function() {
      var currentId = event.currentTarget.id;
      const postInfo = new URLSearchParams();
      postInfo.append("targetId", currentId);

      axios
        .post("http://localhost:8181/deleteItem", postInfo)
        .then(res => {
          axios
            .get("http://localhost:8181/itemList")
            .then(res => {
              this.items = res.data;
            })
            .catch(err => {
              console.error(err);
            });
        })
        .catch(err => {
          console.error(err);
        });
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(done) {
      this.$confirm("Exit the editing?")
        .then(_ => {
          done();
        })
        .catch(_ => {});
    }
  }
};
</script>

<style>
.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.image {
  display: block;
}

.image-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  margin-left: 50%;
  transform: translateX(-50%);
}

.image-uploader .el-upload:hover {
  border-color: #409eff;
}

.image-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.image {
  width: 178px;
  height: 178px;
  display: block;
}

.item-card {
  margin-bottom: 20px;
}

#newItemButton {
  text-align: center;
  position: fixed;
  right: 90px;
  bottom: 90px;
}
</style>
