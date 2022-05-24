<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="Please input a role name: " suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">Search</el-button>
      <el-button type="warning" @click="reset">Reset</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">Add <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5 mr-5"
          title="Are you sure to delete all this?"
          confirm-button-text="Yes"
          cancel-button-text="No"
          icon="el-icon-info"
          icon-color="red"
          @confirm="batchDelete"
      >
        <el-button type="danger" slot="reference">Batch Deletion <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBackground" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="id" label="ID" width="50">
      </el-table-column>
      <el-table-column prop="name" label="Role name" width="120">
      </el-table-column>
      <el-table-column prop="flag" label="Identifier">
      </el-table-column>
      <el-table-column prop="description" label="Description">
      </el-table-column>
      <el-table-column prop="function" label="Function">
        <template v-slot="scope">
          <el-button type="info" @click="selectMenu(scope.row)">Assign menu <i class="el-icon-menu"></i></el-button>
          <el-button type="success" @click="handleEdit(scope.row)">Edit <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              title="Are you sure to delete this?"
              confirm-button-text="Yes"
              cancel-button-text="No"
              icon="el-icon-info"
              icon-color="red"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">Delete <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="Role Info" :visible.sync="dialogFormVisible">
      <el-form label-width="90px" size="small">
        <!--v-model保存form的数据给后台role赋值-->
        <el-form-item label="Role name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Identifier">
          <el-input v-model="form.flag" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Description">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">Cancel</el-button>
              <el-button type="primary" @click="save">Confirm</el-button>
            </span>
    </el-dialog>

    <el-dialog title="Assign Menu" :visible.sync="menuDialogVisible">
      <el-tree
          :props="props"
          :data="menuData"
          show-checkbox
          node-key="id"
          ref="tree"
          :default-expanded-keys="expends"
          :default-checked-keys="checks">
         <span class="custom-tree-node" slot-scope="{ node, data }">
            <span><i :class="data.icon"></i> {{ data.name }}</span>
         </span>
      </el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="menuDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="saveRoleMenu">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Role",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: '',
      dialogFormVisible: false,
      menuDialogVisible: false,
      form: {},
      multipleSelection: [],
      headerBackground: 'headerBackground',
      menuData: [],
      props: {
        label: 'name',
      },
      expends: [],
      checks: [],
      roleId: 0,
      roleFlag: '',
      ids: []
    }
  },
  created() {
    // 请求分页查询数据
    this.load();
  },
  methods: {
    // 加载数据
    load() {
      // 请求分页查询数据
      this.request.get("/role/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total;
      })

      this.request.get("/menu/ids").then(r => {
        this.ids = r.data
      })
    },
    // 保存提交的表单数据
    save() {
      this.request.post("/role", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("Save successfully!")
          this.dialogFormVisible = false
          this.load()
        }
        else this.$message.error("Save failed!")
      })
    },
    // 保存菜单选项, 用key来获取menu的值
    saveRoleMenu() {
      this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
        if (res.code === '200') {
          this.$message.success("Save successfully")
          this.menuDialogVisible = false

          // 操作管理员角色后需要重新登录
          if (this.roleFlag === 'ROLE_ADMIN') {
            this.$store.commit("logout")
          }

        } else {
          this.$message.error(res.msg)
        }
      })
    },
    // 新增
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    // 编辑
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    // 删除
    del(id) {
      this.request.delete("/role/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("Delete successfully!")
          this.load()
        }
        else this.$message.error("Delete failed!")
      })
    },
    // 获取批量删除的信息
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 批量删除
    batchDelete() {
      let ids = this.multipleSelection.map(v => v.id) // [{}, {}, {}] => [1,2,3]
      this.request.post("/role/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("Batch delete successfully!")
          this.load()
        }
        else this.$message.error("Batch delete failed!")
      })
    },
    // 重置对话框
    reset() {
      this.name = ''
      this.load()
    },
    // 调pageSize对应页面变化
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    // 调pageNum对应页面变化
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },
    async selectMenu(role) {
      this.roleId = role.id
      this.roleFlag = role.flag

      // 请求菜单数据
      this.request.get("/menu").then(res => {
        this.menuData = res.data

        // 把后台返回的菜单数据处理成 id数组
        this.expends = this.menuData.map(v => v.id)
      })

      // 请求角色-菜单数据
      this.request.get("/role/roleMenu/" + this.roleId).then(res => {
        this.checks = res.data
        this.ids.forEach(id => {
          if (!this.checks.includes(id)) {
            // 可能会报错：Uncaught (in promise) TypeError: Cannot read properties of undefined (reading 'setChecked')
            this.$nextTick(() => {
              this.$refs.tree.setChecked(id, false)
            })
          }
        })
        this.menuDialogVisible = true
      })
    },
  }
}
</script>

<style scoped>
.headerBackground {
  background: #eee!important;
}
</style>