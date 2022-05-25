<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="Please input a menu name: " suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">Search</el-button>
      <el-button type="warning" @click="reset">Reset</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd(null)">Add <i class="el-icon-circle-plus-outline"></i></el-button>
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

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBackground"
              row-key="id" default-expand-all @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="id" label="ID" width="80">
      </el-table-column>
      <el-table-column prop="name" label="Menu name" width="120">
      </el-table-column>
      <el-table-column prop="path" label="Path" width="120">
      </el-table-column>
      <el-table-column prop="pagePath" label="Page path" width="120">
      </el-table-column>
      <el-table-column label="Icon" align="center" width="60">
        <template v-slot="scope">
          <span :class="scope.row.icon" style="font-size: 16px"/>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="Description">
      </el-table-column>
      <el-table-column prop="function" label="Function">
        <template v-slot="scope">
          <el-button type="primary" @click="handleAdd(scope.row.id)" v-if="!scope.row.pid && !scope.row.path">Add submenu <i class="el-icon-plus"></i></el-button>
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

    <el-dialog title="Menu Info" :visible.sync="dialogFormVisible">
      <el-form label-width="90px" size="small">
        <!--v-model保存form的数据给后台Menu赋值-->
        <el-form-item label="Menu name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Path">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Page path">
          <el-input v-model="form.pagePath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Icon">
          <el-select clearable v-model="form.icon" placeholder="Please choose:" style="width: 100%">
            <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
              <i :class="item.value" /> {{ item.name }}
            </el-option>
          </el-select>
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
  </div>
</template>

<script>
export default {
  name: "Menu",
  data() {
    return {
      tableData: [],
      name: '',
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      headerBackground: 'headerBackground',
      options: [],
    }
  },
  created() {
    // 请求分页查询数据
    this.load();
  },
  methods: {
    // 加载数据
    load() {
      this.request.get("/menu", {
        params: {
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data
      })
      // 请求图标的数据
      this.request.get("/menu/icons").then(res => {
        this.options = res.data
      })
    },
    // 保存提交的表单数据
    save() {
      this.request.post("/menu", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("Save successfully!")
          this.dialogFormVisible = false
          this.load()
        }
        else this.$message.error("Save failed!")
      })
    },
    // 新增
    handleAdd(pid) {
      this.dialogFormVisible = true
      this.form = {}
      if (pid) {
        this.form.pid = pid
      }
    },
    // 编辑
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    // 删除
    del(id) {
      this.request.delete("/menu/" + id).then(res => {
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
      this.request.post("/menu/del/batch", ids).then(res => {
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
  }
}
</script>

<style scoped>
.headerBackground {
  background: #eee!important;
}
</style>