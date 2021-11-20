<?php
$db = new mysqli('localhost', 'root', 'ncis', 'phonebook');
$db->set_charset('utf8');

$result = ['code' => -1];

if (isset($_GET['action'])) {
    switch(strtolower($_GET['action'])) {
        // CREATE
        case 'create':
            $result['code'] = 0;
            if (isset($_POST['name'], $_POST['mobile'], $_POST['address'], $_POST['male'], $_POST['friend'], $_POST['score'])) {
                $name = $db->real_escape_string($_POST['name']);
                $mobile = $db->real_escape_string($_POST['mobile']);
                $address = $db->real_escape_string($_POST['address']);
                $male = $_POST['male'] ? 1 : 0;
                $friend = $_POST['friend'] ? 1 : 0;
                $score = floatval($_POST['score']);
                $db->query("INSERT INTO `persons` VALUES (NULL,'{$name}','{$mobile}','{$address}','{$male}','{$friend}','{$score}')");
                if ($db->affected_rows > 0) {
                    $result = [
                        'code' => 1,
                        'data' => $db->insert_id,
                    ];
                }
            }
            break;
        // READ
        case 'read':
            $result['code'] = 0;
            if (isset($_POST['id'])) {
                $id = intval($_POST['id']);
                $data = $db->query("SELECT * FROM `persons` WHERE (`id`='{$id}')");
                if ($data && $data->num_rows > 0) {
                    $data = $data->fetch_assoc();
                    $result = [
                        'code' => 1,
                        'data' => [$data],
                    ];
                }
            } else {
                $data = [];
                $rows = $db->query("SELECT * FROM `persons` ORDER BY `id`");
                if ($rows && $rows->num_rows > 0) {
                    while ($row = $rows->fetch_assoc()) {
                        $data[] = $row;
                    }
                    $rows->free();
                }
                if (count($data) > 0) {
                    $result = [
                        'code' => 1,
                        'data' => $data,
                    ];
                }
            }
            break;
        // UPDATE
        case 'update':
            $result['code'] = 0;
            if (isset($_POST['id'], $_POST['name'], $_POST['mobile'], $_POST['address'], $_POST['male'], $_POST['friend'], $_POST['score'])) {
                $id = intval($_POST['id']);
                $name = $db->real_escape_string($_POST['name']);
                $mobile = $db->real_escape_string($_POST['mobile']);
                $address = $db->real_escape_string($_POST['address']);
                $male = $_POST['male'] ? 1 : 0;
                $friend = $_POST['friend'] ? 1 : 0;
                $score = floatval($_POST['score']);
                $db->query("UPDATE `persons` SET `name`='{$name}',`mobile`='{$mobile}',`address`='{$address}',`male`='{$male}',`friend`='{$friend}',`score`='{$score}' WHERE (`id`='{$id}')");
                if ($db->affected_rows > 0) {
                    $result['code'] = 1;
                }
            }
            break;
        // DELETE
        case 'delete':
            $result['code'] = 0;
            if (isset($_POST['id'])) {
                $id = intval($_POST['id']);
                $db->query("DELETE FROM `persons` WHERE (`id`='{$id}')");
                if ($db->affected_rows > 0) {
                    $result['code'] = 1;
                }
            }
            break;
    }
}

header ('Content-Type: application/json');
echo json_encode($result);