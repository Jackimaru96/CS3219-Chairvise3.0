<template>
    <div class="sidebar">
        <el-menu style="height:100%" :default-active="$route.path" router>
            <el-submenu index="1">
                <template slot="title"><i class="el-icon-menu"></i>Presentations</template>
                <el-menu-item-group>
                    <el-menu-item :index="'/analyze/__NEW__'">
                        <i class="el-icon-edit-outline"></i>
                        <span slot="title">Create New</span>
                    </el-menu-item>
                    <el-menu-item :index="'/presentationCreated'">
                        <i class="el-icon-edit"></i>
                        <span slot="title">Created By Me</span>
                    </el-menu-item>
                    <el-menu-item :index="'/presentationShared'">
                        <i class="el-icon-s-custom"></i>
                        <span slot="title">Shared With Me</span>
                    </el-menu-item>
                </el-menu-item-group>
            </el-submenu>
            <el-submenu index="2">
                <template slot="title"><i class="el-icon-menu"></i>Files</template>
                <el-menu-item-group>
                    <el-menu-item :index="'/importData'">
                        <i class="el-icon-upload"></i>
                        <span slot="title">Upload New</span>
                    </el-menu-item>
                    <el-menu-item :index="'/fileData'">
                        <i class="el-icon-files"></i>
                        <span slot="title">Added By Me</span>
                    </el-menu-item>
                </el-menu-item-group>
            </el-submenu>
        </el-menu>
    </div>
</template>
<script>
    export default {
        name: "DataSideBar"
    }
</script>

<style scoped>
    .sidebar{
        height: 100%;
    }
</style>